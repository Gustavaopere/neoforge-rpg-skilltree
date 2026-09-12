# Azimuth API

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815f860bc2ff89480d53
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Azimuth API
- **Arquivo JAR:** `azimuth-1.4.8.jar`
- **Versão 1.21.1:** 1.4.8
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Tecnologia
- **Função:** API de infraestrutura para addons Create, com Super Block Entity Behaviours, helpers de advancements e outlines/Ponder sem substituir a autoridade cinética do Create.
- **Dependências:** Create 6.0.10 físico. O consumer anteriormente documentado Bits 'n' Tracks está ausente da modlist atual; Create Tracks+ 1.0.6b6 está presente, mas não foi comprovado como consumer de Azimuth. Não remover a biblioteca sem dependency graph/JAR metadata.
- **Sobreposição:** Biblioteca de infraestrutura; não é conteúdo tecnológico redundante.
- **Compatibilidade/Riscos:** Mixins em SmartBlockEntity, casts de behaviour, double-tick e version drift com Create 6.0.10 são os principais riscos. Outlines/Ponder devem permanecer client-side.
- **Observações:** Runtime físico `azimuth-1.4.8.jar`, display `Azimuth`, mod id `azimuth`; a página editorial permanece `Azimuth API`. Consumer atual não foi inferido por semelhança de nome.
- **Procedência:** modlist.txt física atual de 11/09/2026 + documentação/source Azimuth 1.4.8 + reconciliação física de consumers já auditada. Reconciliação final: JAR/runtime permanecem exatamente `azimuth-1.4.8.jar` / `1.4.8`; display name físico `Azimuth` e título editorial `Azimuth API` são a mesma entrada documentada, sem divergência binária.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/azimuth-api
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #60: `azimuth-1.4.8.jar` / `1.4.8` conferidos contra a modlist atual; display físico `Azimuth`, título editorial `Azimuth API` e consumer graph pendente preservados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Azimuth 1.4.8 como API de infraestrutura do ecossistema Create. Em 09/09/2026, o consumer anteriormente citado Bits 'n' Tracks foi reconciliado como ausente da modlist física atual; nenhum consumer substituto foi presumido. A necessidade de Azimuth permanece dependente de dependency graph/JAR metadata, sem converter presença física em decisão.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `azimuth-1.4.8.jar`, mod id `azimuth`, runtime `1.4.8`, NeoForge 1.21.1. A release oficial 1.4.8 é de 31/08/2026 e o projeto é uma biblioteca para addons Create.

## 1. Papel e autoridade
Azimuth é uma **API de infraestrutura para o ecossistema Create**. Ela estende superfícies do Create para que addons compartilhem comportamentos estruturais, integração de `SmartBlockEntity`, utilidades de advancements e outlines/Ponder. Não adiciona uma progressão tecnológica própria e não deve virar provider de energia, stress ou cinética.

**Create continua autoridade** de kinetic network, stress, speed, contraptions e block entities Create. Azimuth fornece composição/extensão para consumidores.

## 2. Super Block Entity Behaviours
A documentação oficial destaca **Super Block Entity Behaviours**: componentes reutilizáveis acoplados a `SmartBlockEntity`/behaviours do Create. O runtime do pack confirma mixin `azimuth.mixins.json:super_behavior.SmartBlockEntityMixin`, incluindo buscas por super behaviours e extension behaviours.

Contrato operacional:
- o behaviour pertence ao lifecycle da block entity hospedeira;
- criação/invalidação não pode deixar referência órfã após chunk unload;
- uma extensão não deve duplicar tick/kinetic update já executado pelo Create;
- casts/lookup de behaviour precisam falhar de modo seguro quando o tipo esperado não existe.

## 3. Advancements
Azimuth oferece helpers no estilo Create para definição e award de advancements por addons consumidores. A API não deve ser tratada como authority de progressão global do pack: o consumidor define o gatilho e o servidor continua authority da concessão.

## 4. Outlines e Ponder
Outro subsistema oficial são **outliner types**, incluindo animações/visualização usadas especialmente em cenas Ponder. Isso é apresentação client-side; outline/Ponder não pode decidir estado de gameplay nem substituir validação de servidor.

## 5. Release 1.4.8
O changelog oficial da 1.4.8 registra melhoria de logging em crash de class cast. Isso reforça o principal risco da biblioteca: consumidores/mixins assumirem um tipo de behaviour incompatível após atualização de Create ou outro addon.

## 6. Integração com o pack
- Create físico: `6.0.10`.
- Azimuth físico: `1.4.8`.
- O snapshot anterior citava **Bits 'n' Tracks** como consumidor, mas esse mod **não está presente** na modlist física atual de 595 top-levels. A entrada de trilhos atualmente presente é `tracks_plus-1.0.6b6.jar` / Create Tracks+ 1.0.6b6; **não foi confirmada nesta auditoria como consumer de Azimuth**, portanto não será usada como substituição presumida.
- Até que o dependency graph/JAR metadata dos consumidores atuais seja verificado, **não remover Azimuth por inferência de ausência de consumer**.

Provider-native first: addons que realmente dependam de Azimuth devem usar suas superfícies reais; não reimplementar Super Behaviours em mod próprio sem necessidade.

## 7. Client/server e lifecycle
- lógica de block entity/kinetics/advancement é server-authoritative quando afeta gameplay;
- outlines/Ponder/render ficam no cliente;
- chunk load/unload deve registrar e invalidar behaviours uma vez;
- resource reload não deve deixar outline/model cache stale;
- server restart precisa reconstruir extensões a partir do estado da BE, sem persistir ponte inválida.

## 8. Riscos
1. Mixins em `SmartBlockEntity` competindo com outros addons Create.
2. Class cast por behaviour incompatível ou ordem de registro.
3. Double-tick/double-processing se consumidor executar lógica base e extensão separadamente.
4. Version drift entre Azimuth e Create 6.0.10.
5. Carregamento indevido de classes visuais no dedicated server.

## 9. Matriz de testes
1. Dedicated server boot com Create 6.0.10.
2. Block entity consumidora: place/break/chunk unload/reload/server restart.
3. Exactly-once tick/kinetic update.
4. Behaviour ausente ou tipo errado falha fechado e produz log útil.
5. Ponder/outlines após resource reload.
6. Dois addons Azimuth na mesma SmartBlockEntity sem sobrescrever state um do outro.

## 10. Evidência
- modlist física atual: `azimuth-1.4.8.jar`;
- CurseForge oficial Azimuth API 1.4.8;
- documentação oficial das superfícies Super Block Entity Behaviours, Advancements e Outlines;
- logs locais do pack confirmando o mixin de `SmartBlockEntity` e buscas de behaviours.

> 🧩 Exaustividade proporcional: Azimuth é biblioteca, então a ficha cataloga seus contratos de extensão, lifecycle, side e riscos em vez de inventar blocos ou máquinas próprias.