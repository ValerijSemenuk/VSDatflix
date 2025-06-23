class Actor {
  final String id;
  final String name;
  final String biography;
  final String photoUrl;

  Actor({
    required this.id,
    required this.name,
    required this.biography,
    required this.photoUrl,
  });

  factory Actor.fromJson(Map<String, dynamic> json) {
    return Actor(
      id: json['id'] as String,
      name: json['name'] ?? '',
      biography: json['biography'] ?? '',
      photoUrl: json['photoUrl'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'biography': biography,
      'photoUrl': photoUrl,
    };
  }
}
