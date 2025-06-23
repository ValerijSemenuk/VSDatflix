import 'dart:convert';
import 'package:http/http.dart' as http;
import '../../models/genre.dart';

class GenreApi {
  final String baseUrl;

  GenreApi({required this.baseUrl});

  Future<List<Genre>> fetchGenres() async {
    final response = await http.get(Uri.parse('$baseUrl/genres'));
    if (response.statusCode == 200) {
      final List jsonList = jsonDecode(response.body);
      return jsonList.map((e) => Genre.fromJson(e)).toList();
    } else {
      throw Exception('Failed to load genres');
    }
  }

  Future<Genre?> fetchGenreById(String id) async {
    final response = await http.get(Uri.parse('$baseUrl/genres/$id'));
    if (response.statusCode == 200) {
      return Genre.fromJson(jsonDecode(response.body));
    } else {
      return null;
    }
  }

  Future<Genre> createGenre(Genre genre) async {
    final response = await http.post(
      Uri.parse('$baseUrl/genres'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(genre.toJson()),
    );
    if (response.statusCode == 201) {
      return Genre.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to create genre');
    }
  }

  Future<bool> updateGenre(Genre genre) async {
    final response = await http.put(
      Uri.parse('$baseUrl/genres/${genre.id}'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(genre.toJson()),
    );
    return response.statusCode == 200;
  }

  Future<bool> deleteGenre(String id) async {
    final response = await http.delete(Uri.parse('$baseUrl/genres/$id'));
    return response.statusCode == 200;
  }
}
