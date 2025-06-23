import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../config/dart/genre_api.dart';
import '../../models/genre.dart';

final genreApiProvider = Provider<GenreApi>((ref) {
  return GenreApi(baseUrl: 'http://192.168.1.102:8000');
});

final genresProvider = FutureProvider<List<Genre>>((ref) async {
  final api = ref.watch(genreApiProvider);
  return api.fetchGenres();
});
