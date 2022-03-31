package geoscript.workspace

import geoscript.feature.*
import geoscript.geom.*
import geoscript.layer.*
import geoscript.render.*
import geoscript.style.Gradient
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class H2GISTest {

    @Test void createFromPath() {
        H2GIS h2gis = new H2GIS("./target/mydb")
        assertEquals "H2GIS", h2gis.format
        h2gis.getSql().execute("drop table if exists \"widgets\" ")
        Layer l = h2gis.create('widgets',[new Field("geom", "Point"), new Field("name", "String")])
        assertNotNull(l)
        l.add([new Point(1,1), "one"])
        l.add([new Point(2,2), "two"])
        l.add([new Point(3,3), "three"])
        assertEquals 3, l.count()
        h2gis.close()
    }

    @Test void addFeatures() {
        H2GIS h2gis = new H2GIS("./target/mydb")
        h2gis.getSql().execute("drop table if exists \"PARCELS\" ")
        Shapefile countries = new Shapefile(new File(H2GISTest.class.getResource("landcover2000.shp").toURI()))
        h2gis.add(countries, "PARCELS")
        def h2GISTable = h2gis.get("PARCELS");
        assertEquals(countries.count, h2GISTable.count)
    }

    @Test void linkedFile() {
        H2GIS h2gis = new H2GIS("./target/mydb")
        h2gis.getSql().execute("drop table if exists \"PARCELS\" ")
        Shapefile countries = new Shapefile(new File(H2GISTest.class.getResource("landcover2000.shp").toURI()))
        h2gis.linkedFile(H2GISTest.class.getResource("landcover2000.shp").toURI(), "PARCELS", true)
        def h2GISTable = h2gis.get("PARCELS");
        assertEquals(countries.count, h2GISTable.count)
    }

    @Disabled
    @Test
    void readShapeFile(){
        Shapefile countries = new Shapefile(new File(H2GISTest.class.getResource("landcover2000.shp").toURI()))
        println "# Features in Countries = ${countries.count}"
        Gradient gradient = new Gradient(countries, "runoff_win", "Quantile", 3, "Greens")
        countries.style = gradient
        Map map = new Map()
        map.addLayer(countries)
        File imgFile = new File("/tmp/mymap.png")
        map.render(imgFile)

    }
}
