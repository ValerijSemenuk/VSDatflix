import 'dart:convert';
import 'package:http/http.dart' as http;
import '../../models/movie.dart';

class TmdbApi {
  static const _baseUrl = 'http://192.168.1.102:8000'; // Або IP сервера/домен бекенду

  Future<List<Movie>> getPopular(int page) async {
    final res = await http.get(Uri.parse('$_baseUrl/api/movies/popular?page=$page'));
    if (res.statusCode == 200) {
      final List jsonList = jsonDecode(res.body);
      return jsonList.map((e) => Movie.fromJson(e)).toList();
    } else {
      throw Exception('Не вдалося завантажити фільми');
    }
  }

// Додаткові методи, наприклад:
// getMovieDetail(id), searchMovies(query), getGenres(), тощо
}
