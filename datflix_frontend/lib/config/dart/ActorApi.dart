import 'dart:convert';
import 'package:http/http.dart' as http;
import '../../models/actor.dart';

class ActorApi {
  final String baseUrl;

  ActorApi({required this.baseUrl});

  Future<List<Actor>> fetchActors() async {
    final response = await http.get(Uri.parse('$baseUrl/api/actors'));
    if (response.statusCode == 200) {
      final List jsonList = jsonDecode(response.body);
      return jsonList.map((e) => Actor.fromJson(e)).toList();
    } else {
      throw Exception('Failed to load actors');
    }
  }

  Future<Actor?> fetchActorById(String id) async {
    final response = await http.get(Uri.parse('$baseUrl/api/actors/$id'));
    if (response.statusCode == 200) {
      return Actor.fromJson(jsonDecode(response.body));
    } else {
      return null;
    }
  }

  Future<Actor> createActor(Actor actor) async {
    final response = await http.post(
      Uri.parse('$baseUrl/api/actors'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(actor.toJson()),
    );
    if (response.statusCode == 201) {
      return Actor.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to create actor');
    }
  }

  Future<bool> updateActor(Actor actor) async {
    final response = await http.put(
      Uri.parse('$baseUrl/api/actors/${actor.id}'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(actor.toJson()),
    );
    return response.statusCode == 200;
  }

  Future<bool> deleteActor(String id) async {
    final response = await http.delete(Uri.parse('$baseUrl/api/actors/$id'));
    return response.statusCode == 200;
  }
}
