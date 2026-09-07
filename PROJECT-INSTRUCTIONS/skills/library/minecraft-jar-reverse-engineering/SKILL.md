---
name: minecraft-jar-reverse-engineering
source: user-supplied minecraft-jar-reverse-engineering.zip
project_status: preferred
---
# Minecraft JAR Reverse Engineering

Use quando o JAR precisa ser entendido porque source está indisponível, metadata é ambígua, mixins/dependências são suspeitos ou compatibilidade binária precisa ser confirmada.

Inspecione primeiro metadata, manifests, mod IDs, versions, mixins e package layout. Diferencie evidência observada de inferência. Não invente nomes de classe/método/signature. Para patch ou integração, confirme a versão exata do JAR e registre risco de quebra binária.
