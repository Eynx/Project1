package com.revature.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.revature.models.Reimbursement;

import java.io.IOException;

public class ReimbursementSerializer extends StdSerializer<Reimbursement>
{
	public ReimbursementSerializer()
	{
		this(null);
	}

	public ReimbursementSerializer(Class<Reimbursement> reimbursement)
	{
		super(reimbursement);
	}

	@Override
	public void serialize(Reimbursement reimbursement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
	{
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", reimbursement.getId());
		jsonGenerator.writeNumberField("amount", reimbursement.getAmount());
		jsonGenerator.writeStringField("description", reimbursement.getDescription());
		jsonGenerator.writeStringField("status", reimbursement.getStatus().getName());
		jsonGenerator.writeObjectFieldStart("links");
		jsonGenerator.writeStringField("user", "/api/users/" + reimbursement.getUser().getId());
		jsonGenerator.writeEndObject();
		jsonGenerator.writeEndObject();
	}
}
