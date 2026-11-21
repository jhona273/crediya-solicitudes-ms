package co.com.crediya.model.tipoprestamo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TipoPrestamo {
    private Long id;
    private String nombre;
    private BigDecimal montoMinimo;
    private BigDecimal montoMaximo;
    private BigDecimal tasaInteres;       // anual, en porcentaje (15 = 15 %)
    private boolean validacionAutomatica; // la usará HU7

    public boolean admiteMonto(BigDecimal monto) {
        return monto.compareTo(montoMinimo) >= 0 && monto.compareTo(montoMaximo) <= 0;
    }
}
