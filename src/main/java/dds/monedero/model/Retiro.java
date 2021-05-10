package dds.monedero.model;

import dds.monedero.exceptions.MontoNegativoException;
import java.time.LocalDate;

public class Retiro extends Movimiento {

  public Retiro(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public boolean esDeposito() {
    return false;
  }

  @Override
  public double calcularValor(Cuenta cuenta) {
    return cuenta.getSaldo() - getMonto();
  }

  @Override
  public void validarMonto(double monto) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": el monto que se quiere retirar debe ser un valor positivo");
    }
  }
}
