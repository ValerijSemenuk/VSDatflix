import 'package:flutter/material.dart';

class SettingsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF121212), // Темний фон
      appBar: AppBar(
        title: const Text(
          'Налаштування',
          style: TextStyle(color: Color(0xFF00BCD4)),
        ),
        backgroundColor: const Color(0xFF0D1321),
        elevation: 4,
        iconTheme: const IconThemeData(color: Color(0xFF00BCD4)),
      ),
      body: const Center(
        child: Text(
          'Сторінка налаштувань (поки порожня)',
          style: TextStyle(color: Colors.white, fontSize: 18),
        ),
      ),
    );
  }
}