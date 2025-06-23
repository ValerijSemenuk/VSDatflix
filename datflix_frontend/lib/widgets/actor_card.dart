import 'package:flutter/material.dart';
import '../../models/actor.dart';

class ActorCard extends StatelessWidget {
  final Actor actor;

  const ActorCard({required this.actor, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Column(
        children: [
          Expanded(
            child: Image.network(
              actor.photoUrl,
              fit: BoxFit.cover,
              errorBuilder: (_, __, ___) => Icon(Icons.person),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8),
            child: Text(
              actor.name,
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
              style: const TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
        ],
      ),
    );
  }
}
