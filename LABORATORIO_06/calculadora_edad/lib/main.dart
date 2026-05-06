import 'package:flutter/material.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Calculadora de Edad',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: const ColorScheme(
          brightness: Brightness.light,
          primary: Color(0xFFFFD09B),
          onPrimary: Colors.black,
          secondary: Color(0xFFFFB0B0),
          onSecondary: Colors.black,
          error: Colors.red,
          onError: Colors.white,
          background: Color(0xFFFFF7D1),
          onBackground: Colors.black,
          surface: Color(0xFFFFECC8),
          onSurface: Colors.black,
        ),
      ),
      home: const PantallaEdad(),
    );
  }
}

class PantallaEdad extends StatefulWidget {
  const PantallaEdad({super.key});

  @override
  State<PantallaEdad> createState() => _PantallaEdadState();
}

class _PantallaEdadState extends State<PantallaEdad> {
  DateTime? _fechaNacimiento;
  String? _resultado;

  /// SELECTOR DE FECHA MEJORADO
  Future<void> _seleccionarFecha() async {
    final hoy = DateTime.now();

    final fecha = await showDatePicker(
      context: context,
      initialDate: DateTime(2000),
      firstDate: DateTime(1900),
      lastDate: hoy,

      // 🔥 IMPORTANTE: inicia en selector de AÑO
      initialDatePickerMode: DatePickerMode.year,

      helpText: 'Selecciona tu fecha de nacimiento',
      cancelText: 'Cancelar',
      confirmText: 'Aceptar',
    );

    if (fecha != null) {
      setState(() {
        _fechaNacimiento = fecha;
        _resultado = null;
      });
    }
  }

  /// CÁLCULO EXACTO
  void _calcularEdadExacta() {
    if (_fechaNacimiento == null) return;

    final hoy = DateTime.now();

    int anios = hoy.year - _fechaNacimiento!.year;
    int meses = hoy.month - _fechaNacimiento!.month;
    int dias = hoy.day - _fechaNacimiento!.day;

    if (dias < 0) {
      meses--;
      final ultimoMes = DateTime(hoy.year, hoy.month, 0).day;
      dias += ultimoMes;
    }

    if (meses < 0) {
      anios--;
      meses += 12;
    }

    setState(() {
      _resultado = '$anios años, $meses meses y $dias días';
    });
  }

  /// FORMATO DE FECHA
  String _formatearFecha(DateTime fecha) {
    return '${fecha.day.toString().padLeft(2, '0')}/'
        '${fecha.month.toString().padLeft(2, '0')}/'
        '${fecha.year}';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFFFF7D1),
      appBar: AppBar(
        title: const Text('Calculadora de Edad'),
        centerTitle: true,
        backgroundColor: const Color(0xFFFFD09B),
        foregroundColor: Colors.black,
        elevation: 0,
        leading: const Icon(Icons.calculate),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            /// INSTRUCCIÓN
            Card(
              color: const Color(0xFFFFECC8),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(16),
              ),
              child: const Padding(
                padding: EdgeInsets.all(16),
                child: Row(
                  children: [
                    Icon(Icons.info_outline),
                    SizedBox(width: 10),
                    Expanded(
                      child: Text(
                        'Selecciona tu fecha de nacimiento para calcular tu edad exacta',
                        style: TextStyle(fontSize: 16),
                      ),
                    ),
                  ],
                ),
              ),
            ),

            const SizedBox(height: 20),

            /// SELECTOR DE FECHA
            Card(
              child: ListTile(
                leading: const Icon(Icons.calendar_today),
                title: Text(
                  _fechaNacimiento == null
                      ? 'Seleccionar fecha'
                      : _formatearFecha(_fechaNacimiento!),
                ),
                trailing: const Icon(Icons.arrow_drop_down),
                onTap: _seleccionarFecha,
              ),
            ),

            const SizedBox(height: 20),

            /// BOTÓN
            SizedBox(
              width: double.infinity,
              child: FilledButton.icon(
                style: FilledButton.styleFrom(
                  backgroundColor: const Color(0xFFFFD09B),
                  foregroundColor: Colors.black,
                  padding: const EdgeInsets.all(14),
                ),
                onPressed: _calcularEdadExacta,
                icon: const Icon(Icons.calculate),
                label: const Text('Calcular edad exacta'),
              ),
            ),

            const SizedBox(height: 30),

            /// RESULTADO
            if (_resultado != null)
              Card(
                elevation: 3,
                color: const Color(0xFFFFECC8),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(16),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(20),
                  child: Column(
                    children: [
                      const Icon(Icons.person, size: 40),
                      const SizedBox(height: 10),
                      const Text(
                        'Tu edad exacta es:',
                        style: TextStyle(fontSize: 16),
                      ),
                      const SizedBox(height: 10),
                      Text(
                        _resultado!,
                        style: Theme.of(context).textTheme.headlineSmall,
                        textAlign: TextAlign.center,
                      ),
                    ],
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}