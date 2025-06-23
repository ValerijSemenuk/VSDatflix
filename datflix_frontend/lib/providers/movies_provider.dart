import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../../models/movie.dart';

class MoviesNotifier extends StateNotifier<AsyncValue<List<Movie>>> {
  final String baseUrl;

  MoviesNotifier(this.baseUrl) : super(const AsyncValue.loading()) {
    fetchMovies();
  }

  Future<void> fetchMovies() async {
    try {
      state = const AsyncValue.loading();
      final response = await http.get(Uri.parse('$baseUrl/movies'));
      if (response.statusCode == 200) {
        final List<dynamic> data = jsonDecode(response.body);
        final movies = data.map((json) => Movie.fromJson(json)).toList();
        state = AsyncValue.data(movies);
      } else {
        state = AsyncValue.error(
          Exception('Failed to load movies: ${response.statusCode}'),
          StackTrace.current,
        );
      }
    } catch (e, st) {
      state = AsyncValue.error(e, st);
    }
  }


  void filterByTitle(String query) {
    final current = state.value ?? [];
    if (query.isEmpty) {
      fetchMovies();
    } else {
      final filtered = current
          .where((movie) => movie.title?.toLowerCase().contains(query.toLowerCase()) ?? false)
          .toList();
      state = AsyncValue.data(filtered);
    }
  }
}

final moviesProvider = StateNotifierProvider<MoviesNotifier, AsyncValue<List<Movie>>>((ref) {
  return MoviesNotifier('http://192.168.1.102:8000');
});
