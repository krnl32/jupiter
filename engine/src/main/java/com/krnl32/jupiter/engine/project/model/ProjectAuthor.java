package com.krnl32.jupiter.engine.project.model;

public final class ProjectAuthor {
    private final String name;
    private final String email;
    private final String organization;

    public ProjectAuthor(String name, String email, String organization) {
        this.name = name;
        this.email = email;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganization() {
        return organization;
    }

	@Override
	public String toString() {
		return "ProjectAuthor{" +
			"name='" + name + '\'' +
			", email='" + email + '\'' +
			", organization='" + organization + '\'' +
			'}';
	}
}

