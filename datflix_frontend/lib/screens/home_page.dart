import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:datflix_frontend/l10n/app_localizations.dart';
import '../models/movie.dart';
import '../models/genre.dart';
import '../providers/movies_provider.dart';
import '../providers/genres_provider.dart';
import '../widgets/movie_card.dart';
import '../widgets/sidebar.dart';

import '../main.dart';

class HomePage extends ConsumerWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final l10n = AppLocalizations.of(context);
    final currentLocale = ref.watch(localeProvider);
    final moviesAsync = ref.watch(moviesProvider);
    final genresAsync = ref.watch(genresProvider);
    debugPrint('Current locale in HomePage build: ${currentLocale.languageCode}');

    return Scaffold(
      backgroundColor: const Color(0xFF121212),
      appBar: AppBar(
        backgroundColor: const Color(0xFF0D1321),
        elevation: 4,
        leading: Builder(
          builder: (context) => IconButton(
            icon: const Icon(Icons.menu, color: Color(0xFF00BCD4)),
            onPressed: () => Scaffold.of(context).openDrawer(),
          ),
        ),
        iconTheme: const IconThemeData(color: Color(0xFF00BCD4)),
        title: Text(
          'Datflix',
          style: GoogleFonts.anton(
            textStyle: const TextStyle(
              fontSize: 32,
              color: Color(0xFF00BCD4),
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
        centerTitle: true,
      ),
      drawer: const Sidebar(),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: moviesAsync.when(
          data: (movies) {
            if (movies.isEmpty) {
              return const Center(
                child: CircularProgressIndicator(color: Color(0xFF00BCD4)),
              );
            }
            return genresAsync.when(
              data: (genres) {
                return ListView(
                  children: [
                    _buildSliderSection(l10n?.allMovies ?? 'All Movies', movies),
                    const SizedBox(height: 20),
                    for (final genre in genres)
                      _buildSliderSection(
                        genre.name,
                        movies.where((m) => m.genreIds?.contains(genre.id) ?? false).toList(),
                      ),
                  ],
                );
              },
              loading: () => const Center(child: CircularProgressIndicator()),
              error: (error, _) => Center(child: Text('Помилка жанрів: $error')),
            );
          },
          loading: () => const Center(child: CircularProgressIndicator(color: Color(0xFF00BCD4))),
          error: (error, _) => Center(child: Text('Помилка фільмів: $error')),
        ),
      ),
    );
  }

  Widget _buildSliderSection(String title, List<Movie> movies) {
    if (movies.isEmpty) return const SizedBox.shrink();

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8),
          child: Text(
            title,
            style: const TextStyle(
              color: Color(0xFF00BCD4),
              fontSize: 20,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        SizedBox(
          height: 220,
          child: ListView.separated(
            scrollDirection: Axis.horizontal,
            itemCount: movies.length > 10 ? 10 : movies.length,
            separatorBuilder: (_, __) => const SizedBox(width: 8),
            itemBuilder: (context, index) => SizedBox(
              width: 150,
              child: MovieCard(movie: movies[index]),
            ),
          ),
        ),
      ],
    );
  }
}
