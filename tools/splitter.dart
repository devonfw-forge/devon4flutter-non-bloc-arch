import 'dart:io';

main(List<String> arguments) async {
  File input = File(arguments[0]);
  String outputPrefix = arguments[1];
  List<String> lines = await input.readAsLines();
  List<String> headers = getSectionHeaders(lines);
  List<String> sections = splitSections(lines, headers);

  headers = cleanHeaders(headers);

  for (int i = 0; i < headers.length; i++) {
    await writeFile(headers[i], sections[i], outputPrefix);
    print('Updated:\t' + headers[i]);
  }
}

List<String> getSectionHeaders(List<String> lines) {
  List<String> headers =
      lines.where((String line) => isSectionHeader(line)).toList();
  return headers;
}

List<String> splitSections(List<String> lines, List sectionsHeader) {
  List<String> sections = List();
  String currentSection = '';
  for (int i = 0; i < lines.length; i++) {
    bool lastLine = (i == lines.length - 1);
    if (sectionsHeader.contains(lines[i])) {
      // New Section
      sections.add(currentSection);
      currentSection = '';
      continue;
    }
    String append = lastLine ? '' : '\n';
    currentSection = currentSection + lines[i] + append;

    //add final section
    if (lastLine) sections.add(currentSection);
  }
  sections.removeAt(0); //rmv header
  return sections;
}

writeFile(String header, String section, String outputPrefix) async {
  File out = File(outputPrefix + header + '.md');

  //Preserving Header and Footer
  String head = '';
  String foot = '';
  bool inHeader = true;
  bool inRefs = section.contains('<div id="refs"');
  bool lastLineOfHeader = false;
  bool firstPTag = false;
  List<String> lines = await out.readAsLines();

  for (int i = 0; i < lines.length; i++) {
    bool lastLine = (i == lines.length - 1);
    if (lines[i].startsWith('## ')) inHeader = false;
    if (lines[i].startsWith('<p align=')) firstPTag = true;
    if (inRefs) inHeader = false;

    if (!lastLine) {
      if (lines[i + 1].startsWith('## ') && inHeader) lastLineOfHeader = true;
    }

    String append = lastLine | lastLineOfHeader | inRefs ? '' : '\n';
    String prefix = inRefs ? '\n' : '';

    lastLineOfHeader = false;

    if (inHeader) head += lines[i] + append;
    if (firstPTag) foot += prefix + lines[i] + append;
  }

  await out.writeAsString(head + section + foot);
}

List<String> cleanHeaders(List<String> headers) {
  List<String> clean = List();
  headers.forEach((h) => clean.add(h.replaceFirst('# ', '')));

  if (clean.contains("000-Introduction")) {
    clean.remove("000-Introduction");
    clean.insert(0, 'Home');
  }

  return clean;
}

bool isSectionHeader(String line) {
  if (line.isEmpty) return false;
  if (line.length < 2) return false;
  return line[0] == '#' && line[1] == ' ';
}
