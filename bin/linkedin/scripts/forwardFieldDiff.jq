([ .sourceResponse.response.elements[].viewFields | keys ] | flatten | unique) - ([ .evalResponse.response.elements[].viewFields | keys ] | flatten | unique)
