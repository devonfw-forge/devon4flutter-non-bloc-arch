import 'dart:io';

const refMarker = 'ref-';
const lineMarker = '\n';
const flutterMarker = 'flutter';
const flutterDevMarker = '(Flutter Dev Team';
const imgMarker = '/wiki//images/';
const codeMarker = "*Codesnippt ";
const tldrMarker = 'TLDR';
const importantMarker = '⚠';
const sectionMarker = "\n# ";

main(List<String> arguments) async {
  File input = File(arguments[0]);
  String text = await input.readAsString();

  print('Number of Sections:\t' +
      (sectionMarker.allMatches(text).length + 1).toString() +
      '\nImages:\t\t\t' +
      imgMarker.allMatches(text).length.toString() +
      '\nCode Snippets:\t\t' +
      codeMarker.allMatches(text).length.toString() +
      '\nTLDRs:\t\t\t' +
      tldrMarker.allMatches(text).length.toString() +
      '\n⚠ Importants:\t\t' +
      importantMarker.allMatches(text).length.toString() +
      '\nLine Count:\t\t' +
      lineMarker.allMatches(text).length.toString() +
      '\nThe Word Flutter:\t' +
      flutterMarker.allMatches(text.toLowerCase()).length.toString() +
      '\nRefs to Flutter Devs:\t' +
      flutterDevMarker.allMatches(text).length.toString() +
      '\nReferences:\t\t' +
      refMarker.allMatches(text).length.toString());
}
