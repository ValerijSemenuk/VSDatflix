import 'package:flutter/material.dart';
import '../models/genre.dart';

class GenreList extends StatelessWidget {
  final List<Genre> genres;

  const GenreList({required this.genres, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: genres.length,
      itemBuilder: (context, index) {
        final genre = genres[index];
        return ListTile(
          title: Text(genre.name),
        );
      },
    );
  }
}
