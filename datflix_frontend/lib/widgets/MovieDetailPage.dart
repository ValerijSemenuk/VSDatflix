import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:video_player/video_player.dart';
import 'package:chewie/chewie.dart';
import '../models/movie.dart';

class MovieDetailPage extends StatefulWidget {
  final Movie movie;

  const MovieDetailPage({Key? key, required this.movie}) : super(key: key);

  @override
  State<MovieDetailPage> createState() => _MovieDetailPageState();
}

class _MovieDetailPageState extends State<MovieDetailPage> {
  VideoPlayerController? _videoPlayerController;
  ChewieController? _chewieController;
  bool _isVideoAvailable = false;

  @override
  void initState() {
    super.initState();

    final videoUrl = widget.movie.videoUrl;

    if (videoUrl != null && videoUrl.isNotEmpty && !_isYoutubeLink(videoUrl)) {
      _videoPlayerController = VideoPlayerController.network(videoUrl);

      _videoPlayerController!.initialize().then((_) {
        _chewieController = ChewieController(
          videoPlayerController: _videoPlayerController!,
          autoPlay: false,
          looping: false,
          allowFullScreen: true,
          allowMuting: true,
          showControls: true,
          materialProgressColors: ChewieProgressColors(
            playedColor: Colors.cyan,
            handleColor: Colors.cyanAccent,
            backgroundColor: Colors.grey.shade700,
            bufferedColor: Colors.lightBlueAccent,
          ),
          // Можна додати кастомні контролери, якщо треба
        );

        setState(() {
          _isVideoAvailable = true;
        });
      }).catchError((error) {
        debugPrint('Video initialization error: $error');
        setState(() {
          _isVideoAvailable = false;
        });
      });
    }
  }

  bool _isYoutubeLink(String url) {
    return url.contains('youtube.com') || url.contains('youtu.be');
  }

  @override
  void dispose() {
    _chewieController?.dispose();
    _videoPlayerController?.dispose();
    super.dispose();
  }

  Future<void> _launchTrailer(BuildContext context, String url) async {
    final Uri uri = Uri.parse(url);
    if (await canLaunchUrl(uri)) {
      await launchUrl(uri, mode: LaunchMode.externalApplication);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Не вдалося відкрити трейлер')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    const primaryColor = Color(0xFF00BCD4);
    const backgroundColor = Color(0xFF121212);

    final movie = widget.movie;

    return Scaffold(
      backgroundColor: backgroundColor,
      appBar: AppBar(
        backgroundColor: const Color(0xFF0D1321),
        iconTheme: const IconThemeData(color: primaryColor),
        title: Text(
          movie.title ?? '',
          style: const TextStyle(color: primaryColor),
        ),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            if (movie.posterUrl != null && movie.posterUrl!.isNotEmpty)
              ClipRRect(
                borderRadius: BorderRadius.circular(12),
                child: Image.network(movie.posterUrl!),
              ),
            const SizedBox(height: 20),

            Text(
              movie.title ?? '',
              style: const TextStyle(
                color: primaryColor,
                fontSize: 28,
                fontWeight: FontWeight.bold,
              ),
              textAlign: TextAlign.center,
            ),

            if (movie.releaseYear != null) ...[
              const SizedBox(height: 8),
              Text(
                'Рік: ${movie.releaseYear}',
                style: const TextStyle(color: Colors.white70, fontSize: 16),
              ),
            ],
            const SizedBox(height: 20),

            Text(
              movie.description ?? 'Опис відсутній.',
              textAlign: TextAlign.center,
              style: const TextStyle(
                  color: Colors.white70, fontSize: 16, height: 1.5),
            ),
            const SizedBox(height: 30),

            Wrap(
              spacing: 16,
              runSpacing: 10,
              alignment: WrapAlignment.center,
              children: [
                ElevatedButton.icon(
                  onPressed: () {
                    if (movie.trailerUrl != null &&
                        movie.trailerUrl!.isNotEmpty) {
                      _launchTrailer(context, movie.trailerUrl!);
                    } else {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text('Трейлер відсутній')),
                      );
                    }
                  },
                  icon: const Icon(Icons.play_arrow),
                  label: const Text('Трейлер'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: primaryColor,
                    padding: const EdgeInsets.symmetric(
                        vertical: 12, horizontal: 20),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(14)),
                  ),
                ),
                ElevatedButton.icon(
                  onPressed: () {
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(content: Text('Додано до улюблених')),
                    );
                  },
                  icon: const Icon(Icons.favorite_border),
                  label: const Text('Улюблені'),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: primaryColor,
                    padding: const EdgeInsets.symmetric(
                        vertical: 12, horizontal: 20),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(14)),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 30),

            if (_isVideoAvailable && _chewieController != null) ...[
              Align(
                alignment: Alignment.centerLeft,
                child: Padding(
                  padding: const EdgeInsets.only(bottom: 12),
                  child: Text(
                    'Дивитись ${movie.title}',
                    style: const TextStyle(
                      color: primaryColor,
                      fontSize: 22,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),

              ClipRRect(
                borderRadius: BorderRadius.circular(16),
                child: Container(
                  height: 200,  // тут задаємо висоту плеєра
                  width: double.infinity, // ширина на всю доступну ширину
                  child: AspectRatio(
                    aspectRatio: _videoPlayerController!.value.aspectRatio,
                    child: Chewie(controller: _chewieController!),
                  ),
                ),
              ),
            ] else
              const SizedBox.shrink(),

            const SizedBox(height: 40),

            Align(
              alignment: Alignment.centerLeft,
              child: Text(
                'Коментарі (0)',
                style: const TextStyle(
                  color: primaryColor,
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            const SizedBox(height: 10),
            Container(
              width: MediaQuery.of(context).size.width * 0.9,
              padding:
              const EdgeInsets.symmetric(vertical: 14, horizontal: 16),
              decoration: BoxDecoration(
                color: Colors.grey[850],
                borderRadius: BorderRadius.circular(12),
              ),
              child: const Text(
                'Коментарів поки немає.',
                style: TextStyle(color: Colors.white54, fontSize: 15),
                textAlign: TextAlign.center,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
