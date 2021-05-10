package dds.monedero.model;

import java.time.LocalDate;

public abstract class Movimiento {
  private LocalDate fecha;
  private double monto;

  public Movimiento(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.validarMonto(monto);
    this.monto = monto;
  }

  // ?????
  public boolean fueDepositado(LocalDate fecha) {
    return this.esDeposito() && this.esDeLaFecha(fecha);
  }

  // ?????
  public boolean fueExtraido(LocalDate fecha) {
    return this.esExtraccion() && this.esDeLaFecha(fecha);
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(this.calcularValor(cuenta));
    cuenta.agregarMovimiento(this);
  }

  public boolean esExtraccion() {
    return !this.esDeposito();
  }

  // Metodos abstractos
  /////////////////////////////////

  public abstract boolean esDeposito();
  public abstract double calcularValor(Cuenta cuenta);
  public abstract void validarMonto(double monto);

  // Getters y setters
  /////////////////////////////////

  public double getMonto() {
    return this.monto;
  }

  public LocalDate getFecha() {
    return this.fecha;
  }

}
