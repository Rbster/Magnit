package ru.rbster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;

@XmlType(name = "entries")
@XmlRootElement
public class Entries {
    @XmlElement(name = "entry")
    public List<Entry> entries = new LinkedList<>();
}
