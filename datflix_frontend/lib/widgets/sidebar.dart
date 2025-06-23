import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../l10n/app_localizations.dart';
import 'package:firebase_auth/firebase_auth.dart';
import '../screens/home_page.dart';
import '../screens/movies_list_page.dart';
import '../main.dart';


class Sidebar extends ConsumerWidget {
  const Sidebar({Key? key}) : super(key: key);

  void _navigate(BuildContext context, Widget page, {bool replace = false}) {
    Navigator.pop(context);
    if (replace) {
      Navigator.pushReplacement(
          context, MaterialPageRoute(builder: (_) => page));
    } else {
      Navigator.push(context, MaterialPageRoute(builder: (_) => page));
    }
  }

  Future<void> _confirmLogout(BuildContext context) async {
    final l10n = AppLocalizations.of(context);
    final shouldLogout = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(l10n?.logoutConfirmation ?? 'Log out of account?'),
        content: Text(l10n?.logoutSure ?? 'Are you sure you want to log out?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: Text(l10n?.no ?? 'No'),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            child: Text(l10n?.yes ?? 'Yes'),
          ),
        ],
      ),
    );

    if (shouldLogout == true) {
      try {
        await FirebaseAuth.instance.signOut();
        Navigator.of(context).popUntil((route) => route.isFirst);
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Помилка при виході: $e')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final l10n = AppLocalizations.of(context);
    final currentLocale = ref.watch(localeProvider);

    return Drawer(
      backgroundColor: const Color(0xFF212121),
      child: Column(
        children: [
          Expanded(
            child: ListView(
              padding: EdgeInsets.zero,
              children: [
                DrawerHeader(
                  decoration: const BoxDecoration(color: Color(0xFF0D1321)),
                  child: Text(
                    'Datflix',
                    style: const TextStyle(
                      color: Color(0xFF00BCD4),
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                      letterSpacing: 5,
                      shadows: [
                        Shadow(
                          offset: Offset(2, 2),
                          blurRadius: 3,
                          color: Colors.black87,
                        ),
                      ],
                    ),
                  ),
                ),
                ListTile(
                  leading: const Icon(Icons.movie, color: Color(0xFF00BCD4)),
                  title: Text(l10n?.popularMovies ?? 'Popular Movies',
                      style: const TextStyle(color: Colors.white)),
                  onTap: () => _navigate(context, const HomePage(), replace: true),
                ),
                ListTile(
                  leading: const Icon(Icons.category, color: Color(0xFF00BCD4)),
                  title: Text(l10n?.allMovies ?? 'All Movies',
                      style: const TextStyle(color: Colors.white)),
                  onTap: () => _navigate(context, const MoviesListPage()),
                ),
                const Divider(color: Colors.grey),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                  child: Text(
                    l10n?.language ?? 'Language',
                    style: TextStyle(
                        color: Colors.grey[400], fontWeight: FontWeight.bold),
                  ),
                ),
                RadioListTile<Locale>(
                  value: const Locale('uk'),
                  groupValue: currentLocale,
                  title: Text(l10n?.ukrainian ?? 'Ukrainian',
                      style: const TextStyle(color: Colors.white)),
                  activeColor: const Color(0xFF00BCD4),
                  onChanged: (Locale? value) {
                    if (value != null) {
                      debugPrint('Changing locale to: ${value.languageCode}');
                      ref.read(localeProvider.notifier).state = value;
                    }
                  },
                ),
                RadioListTile<Locale>(
                  value: const Locale('en'),
                  groupValue: currentLocale,
                  title: Text(l10n?.english ?? 'English',
                      style: const TextStyle(color: Colors.white)),
                  activeColor: const Color(0xFF00BCD4),
                  onChanged: (Locale? value) {
                    if (value != null) {
                      debugPrint('Changing locale to: ${value.languageCode}');
                      ref.read(localeProvider.notifier).state = value;
                    }
                  },
                ),
              ],
            ),
          ),
          SafeArea(
            child: ListTile(
              leading: const Icon(Icons.logout, color: Color(0xFF00BCD4)),
              title: Text(l10n?.logout ?? 'Logout',
                  style: const TextStyle(color: Colors.white)),
              onTap: () => _confirmLogout(context),
            ),
          ),
        ],
      ),
    );
  }
}
