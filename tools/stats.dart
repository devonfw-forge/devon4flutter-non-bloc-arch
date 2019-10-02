import 'dart:io';

const refMarker = 'ref-';
const lineMarker = '\n';
const imgMarker = '/wiki//images/';
const codeMarker = "Codesnippt ";
const tldrMarker = 'TLDR';
const sectionMarker = "\n# ";

main(List<String> arguments) async {
  File input = File(arguments[0]);
  String text = await input.readAsString();

  print('Sections:\t' +
      sectionMarker.allMatches(text).length.toString() +
      '\nImages:\t\t' +
      imgMarker.allMatches(text).length.toString() +
      '\nCode Snippets:\t' +
      codeMarker.allMatches(text).length.toString() +
      '\nTLDRs:\t\t' +
      tldrMarker.allMatches(text).length.toString() +
      '\nLines:\t\t' +
      lineMarker.allMatches(text).length.toString() +
      '\nReferences:\t' +
      refMarker.allMatches(text).length.toString());
}
