package geoscript.workspace

import geoscript.feature.Field
import geoscript.geom.Point
import geoscript.layer.Layer
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

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
}
