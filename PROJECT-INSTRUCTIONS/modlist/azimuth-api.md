# Azimuth API

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db815f860bc2ff89480d53  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Azimuth API
- **Arquivo JAR:** `azimuth-1.4.8.jar`
- **Versão 1.21.1:** `1.4.8`
- **Categoria:** Biblioteca; Tecnologia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/azimuth-api
- **Função:** API de infraestrutura para addons Create, com Super Block Entity Behaviours, helpers de advancements e outlines/Ponder sem substituir a autoridade cinética do Create.
- **Dependências:** Create; necessidade determinada por consumidores instalados como Bits 'n' Tracks.
- **Compatibilidade/Riscos:** Mixins em SmartBlockEntity, casts de behaviour, double-tick e version drift com Create 6.0.10 são os principais riscos. Outlines/Ponder devem permanecer client-side.
- **Sobreposição:** Biblioteca de infraestrutura; não é conteúdo tecnológico redundante.
- **Observações:** Runtime 1.4.8. Super Block Entity Behaviours, Advancements e Outlines são as superfícies oficiais principais; release 1.4.8 melhora logging de class-cast.
- **Procedência:** Modlist física atual de 07/09/2026 + CurseForge/documentação oficial Azimuth API 1.4.8 + evidência de runtime nos logs locais do pack.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Azimuth 1.4.8 como API de infraestrutura do ecossistema Create, com consumidores presentes no pack e riscos/lifecycle documentados. A presença física não foi convertida automaticamente em decisão de manter, remover ou tornar opcional.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — contratos de API Create, lifecycle, side e riscos de SmartBlockEntity catalogados; metadata antiga de 1.4.7 corrigida.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

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
- Bits 'n' Tracks é consumidor já documentado no pack.

Provider-native first: addons que dependem de Azimuth devem usar suas superfícies reais; não reimplementar Super Behaviours em mod próprio sem necessidade.

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
