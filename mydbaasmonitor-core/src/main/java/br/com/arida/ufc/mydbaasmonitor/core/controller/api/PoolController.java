package main.java.br.com.arida.ufc.mydbaasmonitor.core.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import main.java.br.com.arida.ufc.mydbaasmonitor.common.entity.metric.common.AbstractDatabaseMetric;
import main.java.br.com.arida.ufc.mydbaasmonitor.common.entity.metric.common.AbstractMetric;
import org.reflections.Reflections;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

/**
 * Class that handles requests sent by the API module.
 * @author Daivd Araújo - @araujodavid
 * @version 1.0
 * @since May 2, 2013 
 */

@Resource
@Path("/pool")
public class PoolController {
	
	private Result result;
	
	public PoolController(Result result) {
		this.result = result;
	}

	/**
	 * Method that receives a given type of resource and returns the possible metrics
	 * @param metricsType - e.g.: database, machine, host
	 * @return a JSON of metrics name
	 */
	@Post("/metrics")
	public void poolMetric(String metricsType) {
		Reflections reflections = new Reflections("main.java.br.com.arida.ufc.mydbaasmonitor.common.entity.metric."+metricsType);
		List<String> metricsName = new ArrayList<String>();
		if (metricsType.equals("database")) {			
		    Set<Class<? extends AbstractDatabaseMetric>> classes = reflections.getSubTypesOf(AbstractDatabaseMetric.class);
		    for (Class<? extends AbstractDatabaseMetric> clazz : classes) {
				metricsName.add(clazz.getSimpleName());
			}
		} else {
		    Set<Class<? extends AbstractMetric>> classes = reflections.getSubTypesOf(AbstractMetric.class);
		    for (Class<? extends AbstractMetric> clazz : classes) {
				metricsName.add(clazz.getSimpleName());
			}
		}
		result.use(Results.json()).withoutRoot().from(metricsName).serialize();
	}
	
	/**
	 * Method to query the list of Hosts
	 * @return a JSON of Hosts
	 */
	@Post("/hosts")
	public void poolHost() {
		//TODO
	}
	
	/**
	 * Method to query the list of Virtual Machines
	 * @return a JSON of virtual machines
	 */
	@Post("/machines")
	public void poolVirtualMachine() {
		//TODO
	}
	
	/**
	 * Method to query the list of DBMSs
	 * @return a JSON of DBMSs
	 */
	@Post("/dbmss")
	public void poolDBMS() {
		//TODO
	}
	
	/**
	 * Method to query the list of Databases
	 * @return a JSON of databases
	 */
	@Post("/databases")
	public void poolDatabase() {
		//TODO
	}
	
}