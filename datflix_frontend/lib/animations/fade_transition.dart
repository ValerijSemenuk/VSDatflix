import 'package:flutter/material.dart';

class FadeRoute<T> extends PageRouteBuilder<T> {
  final Widget page;
  FadeRoute(this.page)
      : super(
    pageBuilder: (_, __, ___) => page,
    transitionsBuilder: (_, anim, __, child) =>
        FadeTransition(opacity: anim, child: child),
  );
}
