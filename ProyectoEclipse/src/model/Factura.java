package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Factura {
	
	//Atributos
	
	private String id;
	
	private static int numF;
	
	private double pagoAnticipado = 0;
	
	private double precioLicencias;
	
	private double total;
	
	private double descuento;
	
	private Cliente cliente;
	
	private Alquiler alquiler;
	

	
	//Constructor
	
	public Factura ( Cliente cliente, Alquiler alquiler)
	{
		this.numF++;
		this.id=String.valueOf(numF);
		this.cliente= cliente;
		this.alquiler= alquiler;
		calcularTotal(alquiler);
		
			
	}
	
	//getters
	
	public String getId()
	{
		return this.id;
	}
	
	public double getPagoAnticipado()
	{
		return this.pagoAnticipado;
	}
	
	public double getTotal()
	{
		return this.total;
	}
	
	public Cliente getCliente()
	{
		return this.cliente;
	}
	
	public Alquiler getAlquiler()
	{
		return this.alquiler;
	}
	
	public double getPrecioLicencias()
	{
		return this.precioLicencias;
	}
	
	public double getDescuento()
	{
		return this.descuento;
	}
	
	//setters
	
	public void setPagoAnticipado()
	{
		this.pagoAnticipado= this.total * 0.3;
	}
	
	public void setPrecioLicencias(int num)
	{
		this.precioLicencias= 500*num;
	}
	
	public void setTotal(double total)
	{
		this.total= total;
	}
	
	
	//Metódo para calcular total
	
	private void calcularTotal(Alquiler alquiler)
	{
		double precioTarifa= alquiler.getTarifa().getTarifaTemporada();
		
		double precioExcedente=0;
		Tarifa tarifa = alquiler.getTarifaExcedente();
		if ((tarifa!= null))
		{
			 precioExcedente= alquiler.getTarifaExcedente().getPrecioExcedente();
			
		} 
		
		double precioSeguro=0;
		
		
		if (!(alquiler.getSeguro().isEmpty()))
		{
			for (Seguro seguro: alquiler.getSeguro()) 
				{
					precioSeguro += seguro.getPrecio();
				
				}
			
		} 
		
		double porcentajeRiesgoPrima= Double.parseDouble( alquiler.getCarro().getPorcentajeRiesgoPrima());
		
		double precioRiesgoPrima = porcentajeRiesgoPrima * precioSeguro;
		
		if (alquiler.getReserva()!=null&&alquiler.getReserva().getAppCliente()!= "0" )
		{
			this.descuento = 0.1;
		}
		
		LocalDateTime inicio=alquiler.getFechaInicio();
		LocalDateTime fin=alquiler.getFechaDeb();
		long diffDays=ChronoUnit.DAYS.between(inicio, fin);
		this.total= diffDays*(precioTarifa + precioExcedente + precioSeguro + this.precioLicencias + precioRiesgoPrima);
		
		descuento = descuento*this.total;
		
		this.total = total - descuento;
				
		
	}

}
