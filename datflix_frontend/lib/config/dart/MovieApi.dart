import 'dart:convert';
import 'package:http/http.dart' as http;
import '../../models/movie.dart';


class MovieApi {
  final String baseUrl;

  MovieApi({required this.baseUrl});

  Future<List<Movie>> fetchMovies() async {
    final response = await http.get(Uri.parse('$baseUrl/movies'));
    if (response.statusCode == 200) {
      final List jsonList = jsonDecode(response.body);
      return jsonList.map((e) => Movie.fromJson(e)).toList();
    } else {
      throw Exception('Failed to load movies');
    }
  }

  Future<Movie?> fetchMovieById(String id) async {
    final response = await http.get(Uri.parse('$baseUrl/movies/$id'));
    if (response.statusCode == 200) {
      return Movie.fromJson(jsonDecode(response.body));
    } else {
      return null;
    }
  }

  Future<Movie> createMovie(Movie movie) async {
    final response = await http.post(
      Uri.parse('$baseUrl/movies'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(movie.toJson()),
    );
    if (response.statusCode == 201) {
      return Movie.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to create movie');
    }
  }

  Future<bool> updateMovie(Movie movie) async {
    final response = await http.put(
      Uri.parse('$baseUrl/movies/${movie.id}'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(movie.toJson()),
    );
    return response.statusCode == 200;
  }

  Future<bool> deleteMovie(String id) async {
    final response = await http.delete(Uri.parse('$baseUrl/movies/$id'));
    return response.statusCode == 200;
  }
}
