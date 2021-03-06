package com.oakinvest.b2g.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Represents a bitcoin address.
 *
 * Created by straumat on 10/09/16.
 */
@SuppressWarnings("unused")
@NodeEntity(label = "BitcoinAddress")
public class BitcoinAddress {

    /**
     * ID.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Bitcoin address.
     */
    @Property(name = "address")
    private String address;

    /**
     * Default constructor.
     */
    public BitcoinAddress() {
    }

    /**
     * Constructor.
     *
     * @param newAddress address.
     */
    public BitcoinAddress(final String newAddress) {
        this.address = newAddress;
    }

    /**
     * Getter of id.
     *
     * @return id
     */
    public final Long getId() {
        return id;
    }

    /**
     * Setter of id.
     *
     * @param newId the id to set
     */
    public final void setId(final Long newId) {
        id = newId;
    }

    /**
     * Getter of address.
     *
     * @return address
     */
    public final String getAddress() {
        return address;
    }

    /**
     * Setter of address.
     *
     * @param newAddress the address to set
     */
    public final void setAddress(final String newAddress) {
        address = newAddress;
    }

    /**
     * Using bitcoin address.
     *
     * @param o object
     * @return true if equals
     */
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BitcoinAddress)) {
            return false;
        }

        final BitcoinAddress that = (BitcoinAddress) o;

        return getAddress().equals(that.getAddress());
    }

    /**
     * Using bitcoin address.
     *
     * @return hash
     */
    @Override
    public final int hashCode() {
        return getAddress().hashCode();
    }

    @Override
    public final String toString() {
        return address;
    }

}
