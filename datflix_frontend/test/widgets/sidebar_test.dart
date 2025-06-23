import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter/material.dart';
import 'package:datflix_frontend/widgets/sidebar.dart';
import 'package:datflix_frontend/l10n/app_localizations.dart';
import 'package:datflix_frontend/main.dart';

void main() {
  testWidgets('Sidebar changes locale when radio button tapped', (WidgetTester tester) async {
    await tester.pumpWidget(
      ProviderScope(
        child: MaterialApp(
          localizationsDelegates: const [
            AppLocalizations.delegate,
            GlobalMaterialLocalizations.delegate,
            GlobalWidgetsLocalizations.delegate,
            GlobalCupertinoLocalizations.delegate,
          ],
          supportedLocales: AppLocalizations.supportedLocales,
          home: Scaffold(
            appBar: AppBar(
              leading: Builder(
                builder: (context) => IconButton(
                  icon: const Icon(Icons.menu),
                  onPressed: () => Scaffold.of(context).openDrawer(),
                ),
              ),
            ),
            drawer: const Sidebar(),
          ),
        ),
      ),
    );

    await tester.tap(find.byIcon(Icons.menu));
    await tester.pumpAndSettle();

    final providerContainer = ProviderScope.containerOf(tester.element(find.byType(Sidebar)));

    expect(providerContainer.read(localeProvider), const Locale('uk'));

    final englishRadio = find.widgetWithText(RadioListTile<Locale>, 'English');
    expect(englishRadio, findsOneWidget);
    await tester.tap(englishRadio);
    await tester.pumpAndSettle();

    expect(providerContainer.read(localeProvider), const Locale('en'));

    final ukrainianRadio = find.widgetWithText(RadioListTile<Locale>, 'Ukrainian');
    expect(ukrainianRadio, findsOneWidget);
    await tester.tap(ukrainianRadio);
    await tester.pumpAndSettle();
    expect(providerContainer.read(localeProvider), const Locale('uk'));
  });
}