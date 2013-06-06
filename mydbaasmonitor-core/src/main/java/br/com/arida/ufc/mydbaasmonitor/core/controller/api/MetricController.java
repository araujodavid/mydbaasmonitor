package main.java.br.com.arida.ufc.mydbaasmonitor.core.controller.api;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import main.java.br.com.arida.ufc.mydbaasmonitor.core.repository.MetricRepository;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

/**
 * Class that handles metrics requests sent by the API module.
 * @author Daivd Araújo - @araujodavid
 * @version 2.0
 * @since May 9, 2013 
 */

@Resource
@Path("/metric")
public class MetricController {

	private Result result;
	private MetricRepository metricRepository;
	
	public MetricController(Result result, MetricRepository metricRepository) {
		this.result = result;
		this.metricRepository = metricRepository;
	}
	
	/**
	 * Method to requests about the last collection of a particular metric
	 * @param metricName - Metric name
	 * @param resourceType - Resource type
	 * @param metricType - Metric type
	 * @param resourceID - Resource ID
	 * @param startDatetime - Start time
	 * @param endDatetime - End time
	 * Return json of the metric
	 */
	@Path("/single")
	public void getMetricSingle(String metricName, String resourceType, int metricType, int resourceID, String startDatetime, String endDatetime) {
		Class<?> metricClass = null;
		String sql = null;
		
		try {
			if (resourceType.equals("dbms") || resourceType.equals("database")) {			
				metricClass = Class.forName("main.java.br.com.arida.ufc.mydbaasmonitor.common.entity.metric.database."+metricName);						
			} else {
				metricClass = Class.forName("main.java.br.com.arida.ufc.mydbaasmonitor.common.entity.metric."+resourceType+"."+metricName);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (metricType == MetricRepository.METRIC_MULTI_TYPE) {
			try {			
				sql = this.metricRepository.makeQuerySQL(metricClass, metricType, resourceID, MetricRepository.LAST_COLLECTION, startDatetime, endDatetime);
				List<Object> metric = this.metricRepository.queryMetrics(sql, metricClass);
				result
				.use(Results.json())
				.withoutRoot()
				.from(metric)
				.serialize();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (metricType == MetricRepository.METRIC_SINGLE_TYPE) {
			try {			
				sql = this.metricRepository.makeQuerySQL(metricClass, metricType, resourceID, MetricRepository.LAST_COLLECTION, startDatetime, endDatetime);
				Object metric = this.metricRepository.queryMetric(sql, metricClass);
				
				result
				.use(Results.json())
				.withoutRoot()
				.from(metric)
				.serialize();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	/**
	 * Method to requests collections of a particular metric
	 * @param metricName - Metric name
	 * @param resourceType - Resource type
	 * @param metricType - Metric type
	 * @param resourceID - Resource ID
	 * @param startDatetime - Start time
	 * @param endDatetime - End time
	 * Return a json of the metric list
	 */
	@Path("/multi")
	public void getMetricCollection(String metricName, String resourceType, int resourceID, String startDatetime, String endDatetime) {
		Class<?> metricClass = null;
		String sql = null;
		
		try {
			if (resourceType.equals("dbms") || resourceType.equals("database")) {			
				metricClass = Class.forName("main.java.br.com.arida.ufc.mydbaasmonitor.common.entity.metric.database."+metricName);						
			} else {
				metricClass = Class.forName("main.java.br.com.arida.ufc.mydbaasmonitor.common.entity.metric."+resourceType+"."+metricName);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {			
			sql = this.metricRepository.makeQuerySQL(metricClass, MetricRepository.METRIC_NO_TYPE, resourceID, MetricRepository.ALL_COLLECTION, startDatetime, endDatetime);
			List<Object> metric = this.metricRepository.queryMetrics(sql, metricClass);
			result
			.use(Results.json())
			.withoutRoot()
			.from(metric)
			.serialize();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
