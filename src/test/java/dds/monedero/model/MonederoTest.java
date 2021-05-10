package dds.monedero.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class MonederoTest {
  private Cuenta cuenta;

  @Before
  public void init() {
    cuenta = new Cuenta();
  }

  @Test
  public void sePuedeDepositarDineroEnElMonedero() {
    cuenta.poner(1500);
    Assert.assertEquals(cuenta.getSaldo(), 1500, 0);
  }

  @Test(expected = MontoNegativoException.class)
  public void noSePuedeDepositarUnMontoNegativoEnElMonedero() {
    cuenta.poner(-1500);
  }

  @Test
  public void sePuedenRealizarComoMaximoTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    Assert.assertEquals(cuenta.getSaldo(), 1500 + 456 + 1900, 0);
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void noSePuedeRealizarMasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);
  }

  @Test(expected = SaldoMenorException.class)
  public void noSePuedeExtraerMasDelSaldoDisponible() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void noSePuedeExtraerMasDelLimiteDeExtraccionDiaria() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
  }

  @Test(expected = MontoNegativoException.class)
  public void noSePuedeExtraerUnMontoNegativo() {
    cuenta.sacar(-500);
  }

}