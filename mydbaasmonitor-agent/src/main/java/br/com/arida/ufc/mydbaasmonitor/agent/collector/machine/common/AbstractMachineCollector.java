package main.java.br.com.arida.ufc.mydbaasmonitor.agent.collector.machine.common;

import java.util.TimerTask;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import main.java.br.com.arida.ufc.mydbaasmonitor.agent.entity.common.AbstractMetric;

/**
 * 
 * @author Daivd Araújo
 * @version 1.0
 * @since March 8, 2013
 * 
 */

public abstract class AbstractMachineCollector<T extends AbstractMetric> extends TimerTask {

	//Method to load the entity metric values
	public abstract T loadMetric(Sigar sigar) throws SigarException;	

}