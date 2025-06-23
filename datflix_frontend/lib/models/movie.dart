class Movie {
  final String id;
  final String title;
  final String? posterUrl;
  final List<String>? genreIds;
  final int? releaseYear;
  final String? description;
  final String? trailerUrl;
  final String? videoUrl;

  Movie({
    required this.id,
    required this.title,
    this.posterUrl,
    this.genreIds,
    this.releaseYear,
    this.description,
    this.trailerUrl,
    this.videoUrl,
  });

  factory Movie.fromJson(Map<String, dynamic> json) {
    return Movie(
      id: json['id'].toString(),
      title: json['title'] ?? '',
      posterUrl: json['posterUrl'] != null
          ? json['posterUrl'].toString().startsWith('http')
          ? json['posterUrl']
          : 'http://192.168.1.102:8000${json['posterUrl']}'
          : null,
      genreIds: json['genreIds'] != null
          ? List<String>.from(json['genreIds'].map((e) => e.toString()))
          : null,
      releaseYear: json['releaseYear'] != null
          ? int.tryParse(json['releaseYear'].toString())
          : null,
      description: json['description']?.toString(),
      trailerUrl: json['trailerUrl']?.toString(),
      videoUrl: json['videoUrl']?.toString(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'title': title,
    'posterUrl': posterUrl,
    'genreIds': genreIds,
    'releaseYear': releaseYear,
    'description': description,
    'trailerUrl': trailerUrl,
    'videoUrl': videoUrl,
  };
}
