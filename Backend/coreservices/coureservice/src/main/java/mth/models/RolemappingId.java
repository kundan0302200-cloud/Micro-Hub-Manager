package mth.models;

import java.io.Serializable;
import java.util.Objects;

public class RolemappingId implements Serializable {
	private Long role;
	private Long mid;

	public RolemappingId() {
	}

	public RolemappingId(Long role, Long mid) {
		this.role = role;
		this.mid = mid;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RolemappingId other))
			return false;
		return Objects.equals(role, other.role) && Objects.equals(mid, other.mid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(role, mid);
	}
}
