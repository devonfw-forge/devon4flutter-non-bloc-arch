package com.flutter.counter.service.api.rest;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.springframework.http.MediaType;
import com.flutter.counter.logic.api.to.CounterEto;
import com.flutter.counter.logic.api.to.CounterSearchCriteriaTo;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.List;

/**
 * The service interface for REST calls in order to execute the logic of component {@link Counter}.
 */
@Path("/counter/v1")
public interface CounterRestService {

	/**
	 * Delegates to {@link Counter#findCounter}.
	 *
	 * @param id the ID of the {@link CounterEto}
	 * @return the {@link CounterEto}
	 */
	@GET
	@Path("/counter/{id}/")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public CounterEto getCounter(@PathParam("id")
	long id);

	/**
	 * Delegates to {@link Counter#saveCounter}.
	 *
	 * @param counter the {@link CounterEto} to be saved
	 * @return the recently created {@link CounterEto}
	 */
	@POST
	@Path("/counter/")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public CounterEto saveCounter(@Valid
	CounterEto counter);

	/**
	 * Delegates to {@link Counter#deleteCounter}.
	 *
	 * @param id ID of the {@link CounterEto} to be deleted
	 */
	@DELETE
	@Path("/counter/{id}/")
	public void deleteCounter(@PathParam("id")
	long id);

	/**
	 * Delegates to {@link Counter#findCounterEtos}.
	 *
	 * @param searchCriteriaTo the pagination and search criteria to be used for finding counters.
	 * @return the {@link Page list} of matching {@link CounterEto}s.
	 */
	@Path("/counter/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Page<CounterEto> findCountersByPost(@Valid
	CounterSearchCriteriaTo searchCriteriaTo);

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public void getCounter();

	@DELETE
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public void resetCounter();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public void incCounter(CounterEto counter);

}
