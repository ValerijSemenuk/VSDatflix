import 'package:flutter/material.dart';
import '../models/movie.dart';
import 'MovieDetailPage.dart';
 // Не забудь імпортувати сторінку деталей!

class MovieCard extends StatelessWidget {
  final Movie movie;
  const MovieCard({required this.movie, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final posterUrl = movie.posterUrl;
    final title = movie.title;

    return Card(
      color: const Color(0xFF212121),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(10),
      ),
      elevation: 4,
      clipBehavior: Clip.hardEdge,
      child: InkWell(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => MovieDetailPage(movie: movie),
            ),
          );
        },
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Expanded(
              child: (posterUrl != null && posterUrl.isNotEmpty)
                  ? Image.network(
                posterUrl,
                fit: BoxFit.cover,
                loadingBuilder: (context, child, loadingProgress) {
                  if (loadingProgress == null) return child;
                  return const Center(
                    child: CircularProgressIndicator(
                      color: Color(0xFF00BCD4),
                    ),
                  );
                },
                errorBuilder: (_, __, ___) => Center(
                  child: Icon(Icons.broken_image,
                      size: 50, color: Colors.grey[400]),
                ),
              )
                  : Container(
                color: Colors.grey[800]?.withOpacity(0.1),
              ),
            ),
            Container(
              padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 12),
              color: Colors.black.withOpacity(0.1),
              child: Text(
                title ?? 'Без назви',
                style: const TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 14,
                ),
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                textAlign: TextAlign.center,
                textScaleFactor: MediaQuery.of(context).textScaleFactor,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
