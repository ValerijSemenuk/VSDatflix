// ignore: unused_import
import 'package:intl/intl.dart' as intl;
import 'app_localizations.dart';

// ignore_for_file: type=lint

/// The translations for Ukrainian (`uk`).
class AppLocalizationsUk extends AppLocalizations {
  AppLocalizationsUk([String locale = 'uk']) : super(locale);

  @override
  String get login => 'Вхід';

  @override
  String get logout => 'Вийти';

  @override
  String get register => 'Реєстрація';

  @override
  String get popularMovies => 'Популярні фільми';

  @override
  String get allMovies => 'Всі фільми';

  @override
  String get searchByTitle => 'Пошук по назві...';

  @override
  String get exitConfirmation => 'Ви справді хочете вийти?';

  @override
  String get trailer => 'Трейлер';

  @override
  String get favorites => 'Улюблені';

  @override
  String get unableToOpenTrailer => 'Не вдалося відкрити трейлер';

  @override
  String get noTrailer => 'Трейлер відсутній';

  @override
  String get addedToFavorites => 'Додано до улюблених';

  @override
  String get comments => 'Коментарі (0)';

  @override
  String get noCommentsYet => 'Коментарів поки немає.';

  @override
  String get year => 'Рік:';

  @override
  String get watch => 'Дивитись';

  @override
  String get noDescription => 'Опис відсутній.';

  @override
  String get logoutConfirmation => 'Вийти з акаунту?';

  @override
  String get logoutSure => 'Ви впевнені, що хочете вийти з акаунту?';

  @override
  String get no => 'Ні';

  @override
  String get yes => 'Так';

  @override
  String get ukrainian => 'Українська';

  @override
  String get english => 'Англійська';

  @override
  String get language => 'Мова';
}
