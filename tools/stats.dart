import 'dart:io';

const refMarker = 'ref-';
const lineMarker = '\n';
const flutterMarker = 'flutter';
const flutterDevMarker = '(Flutter Dev Team';
const ndMarker = ' n.d.)';
const brokenMarker = '???';
const imgMarker = '/wiki//images/';
const codeMarker = "*Codesnippt ";
const tldrMarker = 'TLDR';
const importantMarker = '⚠';
const chpMarker = "\n# ";

main(List<String> arguments) async {
  File input = File(arguments[0]);
  String text = await input.readAsString();

  print('Number of Chapters:\t' +
      (chpMarker.allMatches(text).length + 1).toString() +
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
      '\nRefs with No Date:\t' +
      ndMarker.allMatches(text).length.toString() +
      '\nRefs that are Broken:\t' +
      brokenMarker.allMatches(text).length.toString() +
      '\nReferences:\t\t' +
      refMarker.allMatches(text).length.toString());
}
