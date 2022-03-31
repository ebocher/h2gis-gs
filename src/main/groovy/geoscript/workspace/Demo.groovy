package geoscript.workspace

import geoscript.layer.Shapefile
import geoscript.render.Draw
import geoscript.style.Gradient


Shapefile countries = new Shapefile("/home/ebocher/Autres/data/jgb/landcover2000.shp")
println "# Features in Countries = ${countries.count}"
Gradient gradient = new Gradient(countries, "runoff_win", "Quantile", 3, "Greens")
countries.style = gradient
def file =  new File("/tmp/map.png")
Draw.draw(countries, bounds: countries.bounds, proj: "EPSG:27572", size: [400, 800], out: file)

