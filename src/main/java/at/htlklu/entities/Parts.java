package at.htlklu.entities;

import jakarta.persistence.*;

@Entity
@Table(name="parts")
public class Parts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "serialnr", nullable = false, length = 12)
    private String serialnr;
    @Basic
    @Column(name = "partname", nullable = false, length = 120)
    private String partname;
    @Basic
    @Column(name = "box", nullable = false, length = 10)
    private String box;
    @Basic
    @Column(name = "count", nullable = false)
    private Integer count;

    public Parts(){}

    public Parts(String serialnr, String partname, String box, Integer count) {
        this.serialnr = serialnr;
        this.partname = partname;
        this.box = box;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Parts{" +
                "id=" + id +
                ", serialnr='" + serialnr + '\'' +
                ", partname='" + partname + '\'' +
                ", box='" + box + '\'' +
                ", count=" + count +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialnr() {
        return serialnr;
    }

    public void setSerialnr(String serialnr) {
        this.serialnr = serialnr;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parts parts = (Parts) o;

        if (id != null ? !id.equals(parts.id) : parts.id != null) return false;
        if (serialnr != null ? !serialnr.equals(parts.serialnr) : parts.serialnr != null) return false;
        if (partname != null ? !partname.equals(parts.partname) : parts.partname != null) return false;
        if (box != null ? !box.equals(parts.box) : parts.box != null) return false;
        if (count != null ? !count.equals(parts.count) : parts.count != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (serialnr != null ? serialnr.hashCode() : 0);
        result = 31 * result + (partname != null ? partname.hashCode() : 0);
        result = 31 * result + (box != null ? box.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
