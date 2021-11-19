package com.example.petstore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {
	List<Pet> pets = new ArrayList<Pet>();

	public PetResource() {
		Pet pet1 = new Pet();
		pet1.setPetId(1);
		pet1.setPetAge(3);
		pet1.setPetName("Boola");
		pet1.setPetType("Dog");

		Pet pet2 = new Pet();
		pet2.setPetId(2);
		pet2.setPetAge(4);
		pet2.setPetName("Sudda");
		pet2.setPetType("Cat");

		Pet pet3 = new Pet();
		pet3.setPetId(3);
		pet3.setPetAge(2);
		pet3.setPetName("Peththappu");
		pet3.setPetType("Bird");

		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet")))})
	@GET
	public Response getPets() {
		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.")})
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Pet pet = new Pet();
		pet.setPetId(petId);
		pet.setPetAge(3);
		pet.setPetName("Buula");
		pet.setPetType("Dog");

		return Response.ok(pet).build();

	}

	//to add a pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Add pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Not found.")})
	@POST
	@Path("/addPet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPet(@RequestBody Pet pet) {
		pets.add(pet);
		return Response.ok(pets).build();
	}


	//to delete a pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Delete a pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Not found.")})
	@DELETE
	@Path("/deletePet/{petID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response deletePet(Pet pet) {
		int petId = pet.getPetId();
		if (pet == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		pets.remove((petId - 1));
		return Response.ok().build();
	}

	//to get pet age
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Get Pet Age", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Not found.")})
	@GET
	@Path("age/{age}")
	public Response getPetAge(int age) {

		List<Pet> filteredAgeData = pets.stream().filter(pet -> pet.getPetAge() == age).collect(Collectors.toList());
		return Response.ok(filteredAgeData).build();

	}

	//to pet name
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Get Pat Name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Not found.")})
	@GET
	@Path("name/{name}")
	public Response getPetName(String name) {

		List<Pet> filteredNameData = pets.stream().filter(pet -> pet.getPetName() == name).collect(Collectors.toList());
		return Response.ok(filteredNameData).build();

	}

	//to get the type of pets
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Get Pat Type", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "Not found.")})
	@GET
	@Path("type/{type}")
	public Response getPetType(String type) {

		List<Pet> filteredTypeData = pets.stream().filter(pet -> pet.getPetType() == type).collect(Collectors.toList());
		return Response.ok(filteredTypeData).build();

	}
}