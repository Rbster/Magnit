package ru.rbster.XMLEntity;

import javax.xml.bind.annotation.XmlElement;

public class Entry {
    @XmlElement(name = "field")
    public Long field;
    public Entry(){}
}
