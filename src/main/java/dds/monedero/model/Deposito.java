package dds.monedero.model;

import dds.monedero.exceptions.MontoNegativoException;
import java.time.LocalDate;

public class Deposito extends Movimiento{
  public Deposito(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public double calcularValor(Cuenta cuenta) {
    return cuenta.getSaldo() + getMonto();
  }

  @Override
  public boolean esDeposito() {
    return true;
  }

  @Override
  public void validarMonto(double monto) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": el monto que se quiere depositar debe ser un valor positivo");
    }
  }

}
