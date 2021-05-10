package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos;

  public Cuenta(double montoInicial) {
    this.validarSaldoInicial(montoInicial);
    this.setMovimientos(new ArrayList<>());
  }

  public Cuenta() {
    this(0);
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {
    this.validarMonto(cuanto);
    this.validarCantidadDeDepositos();
    new Deposito(LocalDate.now(), cuanto).agregateA(this);

  }

  public void sacar(double cuanto) {
    this.validarMonto(cuanto);
    this.validarExtraccion(cuanto);
    this.validarLimiteDeExtraccion(cuanto);
    new Retiro(LocalDate.now(), cuanto).agregateA(this);
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  private long cantidadDeDepositosDeCuenta() {
    return this.getMovimientos()
        .stream()
        .filter(Movimiento::esDeposito)
        .count();
  }

  // Getters y setters
  /////////////////////////////////

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos()
        .stream()
        .filter(movimiento -> !movimiento.esDeposito() && movimiento.esDeLaFecha(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return this.movimientos;
  }

  public double getSaldo() {
    return this.saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  // Validaciones
  /////////////////////////////////

  private void validarSaldoInicial(double saldo) {
    if(saldo < 0) {
      this.saldo = 0;
    }
    this.saldo = saldo;
  }

  private void validarCantidadDeDepositos() {
    if (this.cantidadDeDepositosDeCuenta() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  private void validarMonto(double monto) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  private void validarLimiteDeExtraccion(double cuanto) {
    double limite = 1000 - getMontoExtraidoA(LocalDate.now());
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000 + " diarios, límite: " + limite);
    }
  }

  private void validarExtraccion(double cuanto) {
    if (this.getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + this.getSaldo() + " $");
    }
  }

}
