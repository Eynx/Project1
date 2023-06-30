package com.revature.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.revature.models.Person;

import java.io.IOException;

public class PersonSerializer extends StdSerializer<Person>
{
	public PersonSerializer()
	{
		this(null);
	}

	public PersonSerializer(Class<Person> person)
	{
		super(person);
	}

	@Override
	public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
	{
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", person.getId());
		jsonGenerator.writeStringField("firstName", person.getFirstName());
		jsonGenerator.writeStringField("lastName", person.getLastName());
		jsonGenerator.writeStringField("role", person.getRole().getName());
		jsonGenerator.writeStringField("username", person.getUsername());
		jsonGenerator.writeStringField("password", person.getPassword());
		jsonGenerator.writeObjectFieldStart("links");
		jsonGenerator.writeStringField("reimbursements", "/api/users/" + person.getId() + "/reimbursements");
		jsonGenerator.writeEndObject();
		jsonGenerator.writeEndObject();
	}
}
