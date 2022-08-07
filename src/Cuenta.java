import java.math.BigDecimal;

public class Cuenta {

    private String IBAN;
    private String DNI;
    private String name, apellido;
    private BigDecimal saldo = new BigDecimal(0.0d);

    public Cuenta() {
        this.setIBAN("");
        this.setDNI("");
        this.setName("");
        this.setApellido("");
    }

    public Cuenta(String IBAN, String DNI, String name, String apellido) {
        this.setIBAN(IBAN);
        this.setDNI(DNI);
        this.setName(name);
        this.setApellido(apellido);
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
/*
    public void setSaldo(BigDecimal saldo) {
        this.saldo = this.saldo.add(saldo);
    }
*/

    public String getIBAN() {
        return IBAN;
    }

    public String getDNI() {
        return DNI;
    }

    public String getName() {
        return name;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSaldo() {
        return saldo.toString();
    }

    public BigDecimal getSaldoBigDecimal() {
        return this.saldo;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "IBAN='" + IBAN + '\'' +
                ", DNI='" + DNI + '\'' +
                ", name='" + name + '\'' +
                ", apellido='" + apellido + '\'' +
                ", saldo=" + saldo +
                '}';
    }

    public void addSaldo(BigDecimal cantidad) {
        this.saldo = this.saldo.add(cantidad);
    }

    public boolean restaSaldo( BigDecimal cantidad) {
        if (this.saldo.compareTo(cantidad) > 0) {
            this.saldo = this.saldo.subtract(cantidad);
            return true;
        }
        return false;
    }
}
