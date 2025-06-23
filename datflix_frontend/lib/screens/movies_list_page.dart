import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../models/movie.dart';
import '../providers/movies_provider.dart';
import '../widgets/movie_card.dart';
import '../widgets/sidebar.dart';
import 'package:datflix_frontend/l10n/app_localizations.dart'; // Імпорт

class MoviesListPage extends ConsumerStatefulWidget {
  const MoviesListPage({Key? key}) : super(key: key);

  @override
  ConsumerState<MoviesListPage> createState() => _MoviesListPageState();
}

class _MoviesListPageState extends ConsumerState<MoviesListPage> {
  bool _isSearching = false;
  final TextEditingController _searchController = TextEditingController();

  void _startSearch() {
    setState(() {
      _isSearching = true;
    });
  }

  void _stopSearch() {
    setState(() {
      _isSearching = false;
      _searchController.clear();
      ref.read(moviesProvider.notifier).filterByTitle('');
    });
  }

  void _onSearchChanged(String query) {
    ref.read(moviesProvider.notifier).filterByTitle(query);
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context);
    final moviesAsync = ref.watch(moviesProvider);
    print('MoviesAsync state: $moviesAsync'); // Діагностика

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
        title: _isSearching
            ? TextField(
          controller: _searchController,
          autofocus: true,
          style: const TextStyle(color: Colors.white),
          decoration: InputDecoration(
            hintText: l10n?.searchByTitle ?? 'Search by title...',
            hintStyle: TextStyle(color: Colors.grey[400]),
            border: InputBorder.none,
          ),
          onChanged: _onSearchChanged,
        )
            : Text(
          l10n?.allMovies ?? 'All Movies',
          style: const TextStyle(
            color: Color(0xFF00BCD4),
            fontWeight: FontWeight.bold,
          ),
        ),
        actions: [
          if (_isSearching)
            IconButton(
              icon: const Icon(Icons.close, color: Color(0xFF00BCD4)),
              onPressed: _stopSearch,
            )
          else
            IconButton(
              icon: const Icon(Icons.search, color: Color(0xFF00BCD4)),
              onPressed: _startSearch,
            ),
        ],
        iconTheme: const IconThemeData(color: Color(0xFF00BCD4)),
      ),
      drawer: const Sidebar(),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: moviesAsync.when(
          data: (movies) {
            if (movies.isEmpty) {
              return const Center(
                child: Text('No movies available', style: TextStyle(color: Color(0xFF00BCD4))),
              ); // Заміни на текст для діагностики
            }
            return GridView.builder(
              itemCount: movies.length,
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: MediaQuery.of(context).size.width > 600 ? 3 : 2,
                crossAxisSpacing: 8,
                mainAxisSpacing: 8,
                childAspectRatio: 0.65,
              ),
              itemBuilder: (context, index) {
                final movie = movies[index];
                return MovieCard(movie: movie);
              },
            );
          },
          loading: () => const Center(child: CircularProgressIndicator(color: Color(0xFF00BCD4))),
          error: (error, _) => Center(child: Text('Error: $error', style: TextStyle(color: Color(0xFF00BCD4)))),
        ),
      ),
    );
  }
}