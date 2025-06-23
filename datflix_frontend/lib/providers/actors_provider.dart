import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../models/actor.dart';
import '../config/dart/ActorApi.dart';

final actorApiProvider = Provider<ActorApi>((ref) {
  return ActorApi(baseUrl: 'http://10.0.2.2:8080');
});

final actorsProvider = FutureProvider<List<Actor>>((ref) async {
  final api = ref.watch(actorApiProvider);
  return api.fetchActors();
});
