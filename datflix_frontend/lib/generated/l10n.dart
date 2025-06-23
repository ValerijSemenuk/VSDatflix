// GENERATED CODE - DO NOT MODIFY BY HAND
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'intl/messages_all.dart';

// **************************************************************************
// Generator: Flutter Intl IDE plugin
// Made by Localizely
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, lines_longer_than_80_chars
// ignore_for_file: join_return_with_assignment, prefer_final_in_for_each
// ignore_for_file: avoid_redundant_argument_values, avoid_escaping_inner_quotes

class S {
  S();

  static S? _current;

  static S get current {
    assert(_current != null,
        'No instance of S was loaded. Try to initialize the S delegate before accessing S.current.');
    return _current!;
  }

  static const AppLocalizationDelegate delegate = AppLocalizationDelegate();

  static Future<S> load(Locale locale) {
    final name = (locale.countryCode?.isEmpty ?? false)
        ? locale.languageCode
        : locale.toString();
    final localeName = Intl.canonicalizedLocale(name);
    return initializeMessages(localeName).then((_) {
      Intl.defaultLocale = localeName;
      final instance = S();
      S._current = instance;

      return instance;
    });
  }

  static S of(BuildContext context) {
    final instance = S.maybeOf(context);
    assert(instance != null,
        'No instance of S present in the widget tree. Did you add S.delegate in localizationsDelegates?');
    return instance!;
  }

  static S? maybeOf(BuildContext context) {
    return Localizations.of<S>(context, S);
  }

  /// `Login`
  String get login {
    return Intl.message(
      'Login',
      name: 'login',
      desc: '',
      args: [],
    );
  }

  /// `Logout`
  String get logout {
    return Intl.message(
      'Logout',
      name: 'logout',
      desc: '',
      args: [],
    );
  }

  /// `Register`
  String get register {
    return Intl.message(
      'Register',
      name: 'register',
      desc: '',
      args: [],
    );
  }

  /// `Popular Movies`
  String get popularMovies {
    return Intl.message(
      'Popular Movies',
      name: 'popularMovies',
      desc: '',
      args: [],
    );
  }

  /// `All Movies`
  String get allMovies {
    return Intl.message(
      'All Movies',
      name: 'allMovies',
      desc: '',
      args: [],
    );
  }

  /// `Search by title...`
  String get searchByTitle {
    return Intl.message(
      'Search by title...',
      name: 'searchByTitle',
      desc: '',
      args: [],
    );
  }

  /// `Do you really want to log out?`
  String get exitConfirmation {
    return Intl.message(
      'Do you really want to log out?',
      name: 'exitConfirmation',
      desc: '',
      args: [],
    );
  }

  /// `Trailer`
  String get trailer {
    return Intl.message(
      'Trailer',
      name: 'trailer',
      desc: '',
      args: [],
    );
  }

  /// `Favorites`
  String get favorites {
    return Intl.message(
      'Favorites',
      name: 'favorites',
      desc: '',
      args: [],
    );
  }

  /// `Unable to open trailer`
  String get unableToOpenTrailer {
    return Intl.message(
      'Unable to open trailer',
      name: 'unableToOpenTrailer',
      desc: '',
      args: [],
    );
  }

  /// `No trailer available`
  String get noTrailer {
    return Intl.message(
      'No trailer available',
      name: 'noTrailer',
      desc: '',
      args: [],
    );
  }

  /// `Added to favorites`
  String get addedToFavorites {
    return Intl.message(
      'Added to favorites',
      name: 'addedToFavorites',
      desc: '',
      args: [],
    );
  }

  /// `Comments (0)`
  String get comments {
    return Intl.message(
      'Comments (0)',
      name: 'comments',
      desc: '',
      args: [],
    );
  }

  /// `No comments yet.`
  String get noCommentsYet {
    return Intl.message(
      'No comments yet.',
      name: 'noCommentsYet',
      desc: '',
      args: [],
    );
  }

  /// `Year:`
  String get year {
    return Intl.message(
      'Year:',
      name: 'year',
      desc: '',
      args: [],
    );
  }

  /// `Watch`
  String get watch {
    return Intl.message(
      'Watch',
      name: 'watch',
      desc: '',
      args: [],
    );
  }

  /// `No description available.`
  String get noDescription {
    return Intl.message(
      'No description available.',
      name: 'noDescription',
      desc: '',
      args: [],
    );
  }

  /// `Log out of account?`
  String get logoutConfirmation {
    return Intl.message(
      'Log out of account?',
      name: 'logoutConfirmation',
      desc: '',
      args: [],
    );
  }

  /// `Are you sure you want to log out?`
  String get logoutSure {
    return Intl.message(
      'Are you sure you want to log out?',
      name: 'logoutSure',
      desc: '',
      args: [],
    );
  }

  /// `No`
  String get no {
    return Intl.message(
      'No',
      name: 'no',
      desc: '',
      args: [],
    );
  }

  /// `Yes`
  String get yes {
    return Intl.message(
      'Yes',
      name: 'yes',
      desc: '',
      args: [],
    );
  }

  /// `Ukrainian`
  String get ukrainian {
    return Intl.message(
      'Ukrainian',
      name: 'ukrainian',
      desc: '',
      args: [],
    );
  }

  /// `English`
  String get english {
    return Intl.message(
      'English',
      name: 'english',
      desc: '',
      args: [],
    );
  }

  /// `Language`
  String get language {
    return Intl.message(
      'Language',
      name: 'language',
      desc: '',
      args: [],
    );
  }
}

class AppLocalizationDelegate extends LocalizationsDelegate<S> {
  const AppLocalizationDelegate();

  List<Locale> get supportedLocales {
    return const <Locale>[
      Locale.fromSubtags(languageCode: 'en'),
      Locale.fromSubtags(languageCode: 'uk'),
    ];
  }

  @override
  bool isSupported(Locale locale) => _isSupported(locale);
  @override
  Future<S> load(Locale locale) => S.load(locale);
  @override
  bool shouldReload(AppLocalizationDelegate old) => false;

  bool _isSupported(Locale locale) {
    for (var supportedLocale in supportedLocales) {
      if (supportedLocale.languageCode == locale.languageCode) {
        return true;
      }
    }
    return false;
  }
}
