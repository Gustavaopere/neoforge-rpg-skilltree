# Presence Footsteps x Sable

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81759d95dd87ff071de4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `pfsable-1.0.jar`, mod id `pfsable`, runtime `1.0`; Presence Footsteps 1.12.0-beta.1, Sable 2.0.5 e Create Aeronautics 1.3.2 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, `pfsable-1.0.jar`, Presence Footsteps 1.12.0-beta.1, Sable 2.0.5 e Create Aeronautics 1.3.2 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Presence Footsteps x Sable
- **Arquivo JAR:** `pfsable-1.0.jar`
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, QoL
- **Função:** Bridge de áudio entre Presence Footsteps e Sable/Aeronautics para que superfícies/contraptions físicas produzam sons de passos adequados.
- **Dependências:** Prerequisites funcionais: Presence Footsteps + Sable/Create Aeronautics, todos presentes fisicamente. O projeto CurseForge declara 0 hard dependencies; não converter prerequisites funcionais em metadata de manifest.
- **Sobreposição:** Não é outro footsteps system; bridge específica Presence Footsteps ↔ Sable/Aeronautics. Outros audio mods só se sobrepõem no mix/apresentação.
- **Compatibilidade/Riscos:** Client-side audio bridge. Riscos: fork/API drift, Sable SubLevel surface lookup, ship↔world boundary stale state, double footsteps e resource-pack sound mapping. Não altera physics/gameplay.
- **Observações:** Runtime 1.0, file ID 8152593, única Release NeoForge 1.21.1 de 27/05/2026. Pack físico usa PresenceFootsteps 1.12.0-beta.1, Sable 2.0.5 e Create Aeronautics 1.3.2.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Presence Footsteps x Sable 1.0 + presença física dos três sistemas envolvidos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/presence-footsteps-x-sable-aeronautics-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Presence Footsteps x Sable 1.0 reconstruído: functional prerequisites, surface resolution/SubLevels, client boundary, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `pfsable-1.0.jar`, mod id `pfsable`, versão `1.0`, NeoForge 1.21.1. Este mod é uma bridge **client-side de áudio**: faz Presence Footsteps usar sons adequados sobre contraptions Sable/Create Aeronautics. Não cria um segundo sistema de footsteps nem altera a física das contraptions.

## 1. Identidade e papel
- **Mod:** Presence Footsteps x Sable.
- **JAR físico:** `pfsable-1.0.jar`.
- **Mod id:** `pfsable`.
- **Runtime:** `1.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** jDynamo.
- **CurseForge project ID:** 1555269.
- **Ambiente:** Client.
- **Licença:** MIT.
- **Papel:** adaptar a seleção/reprodução de sons de footsteps do Presence Footsteps quando o jogador pisa em superfícies pertencentes a contraptions Sable/Aeronautics.
- **Decisão:** Sem decisão.

## 2. Prerequisites funcionais versus manifest
A página CurseForge da bridge publica **0 dependencies declaradas** no sistema de relações. Isso não muda sua natureza funcional: sem Presence Footsteps e sem Sable/Aeronautics, não há interseção útil a adaptar.

No pack físico estão presentes:
- `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar`;
- `sable-neoforge-1.21.1-2.0.5.jar`;
- `create-aeronautics-bundled-1.21.1-1.3.2.jar`.

Logo a bridge possui contexto operacional real, mesmo sem hard-dependency metadata no projeto.

## 3. Presence Footsteps continua authority do áudio
Presence Footsteps escolhe e reproduz o sistema de sons de passo. A bridge não deve duplicar banco sonoro nem implementar outro footsteps engine.

Ownership:
- Presence Footsteps → lógica/sons de footsteps;
- Sable/Aeronautics → contraptions/SubLevels/physics;
- pfsable → adaptação da superfície/contexto para que Presence Footsteps reconheça o bloco sob o player.

## 4. Sable/Aeronautics continua authority da contraption
Se uma ship/contraption move, colide ou transforma coordenadas incorretamente, o problema não pertence automaticamente à bridge de áudio.

pfsable deve apenas consultar/traduzir o contexto de contato necessário para produzir o som apropriado.

## 5. Forks Presence Footsteps suportados
A página oficial afirma compatibilidade com duas famílias de fork NeoForge/Forge em 1.21.1:
- 1Influence / Paintninja Presence Footsteps [Forge];
- ZCRAFT Presence Footsteps (NeoForge).

O pack usa a build física `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar`; o smoke deve verificar esse artefato exato em vez de presumir equivalência por nome.

## 6. Footstep surface resolution
A finalidade prática é evitar que, dentro/sobre uma contraption, o sistema de footsteps perca a superfície real e use som errado/vanilla por não enxergar corretamente os blocos do SubLevel.

Cenários importantes:
- caminhar sobre madeira/metal/pedra dentro de ship;
- transitar ship→mundo e mundo→ship;
- bloco modded com sound type específico;
- plataforma em movimento/rotação.

## 7. Client-only boundary
A bridge é Client. Sons de footsteps não alteram:
- movimento;
- colisão;
- stamina;
- detecção server-side de bloco;
- stealth/AI, salvo se outro mod explicitamente usar som como gameplay — o que não foi demonstrado aqui.

Dois clientes podem ter audio mods/config diferentes e ainda compartilhar o mesmo state funcional do servidor.

## 8. Coordenadas e SubLevels
Sable representa contraptions em contexto próprio/SubLevel. A bridge precisa resolver a superfície correta apesar da transformação entre espaço da contraption e mundo.

Riscos de integração:
- som do bloco abaixo no mundo em vez da ship;
- som atrasado ao cruzar boundary;
- som persistindo após desmontar/teleportar;
- posição/rotação fazendo lookup no bloco errado.

A ficha não inventa internals do transform/mixin sem source exato pinado.

## 9. Relação com outros mods de áudio
AmbientSounds e outros ambience/audio mods têm domínio diferente. pfsable trata especificamente **footsteps sobre Sable/Aeronautics**.

Sobreposição auditável é mix/z-order/volume, não duplicação do sistema-base. Se dois mods reproduzirem passo simultâneo, identificar quem emite cada evento antes de remover a bridge.

## 10. Lifecycle
Testar:
- entrar no mundo;
- montar/embarcar numa contraption;
- caminhar/correr/sneak sobre vários materiais;
- cruzar edge ship↔world;
- assembly/disassembly;
- teleport/dimension change;
- reconnect;
- resource reload do audio pack.

A bridge não deve deixar som preso ou continuar tratando o player como estando na ship após sair.

## 11. Release 1.0
Há uma única release publicada para 1.21.1: `Presence Footsteps x Sable 1.0`, file ID `8152593`, de 27/05/2026.

A descrição é objetiva: **“Simple patch to use Presence Footsteps sounds on Sable/Aeronautics contraptions.”** Não há changelog amplo que sustente features adicionais; esta ficha permanece proporcional a esse escopo.

## 12. Riscos
1. **Fork drift:** Presence Footsteps físico pode divergir do fork/test baseline do autor.
2. **Sable API drift:** updates de Sable/Aeronautics podem mudar surface/context lookup.
3. **Wrong surface sound:** transform de SubLevel resolve bloco incorreto.
4. **Boundary stale state:** sair da contraption mantém contexto de áudio antigo.
5. **Double footsteps:** outro audio patch também injeta no mesmo evento.
6. **Resource-pack sound IDs:** pack de sons pode não conter mapping esperado.
7. **No manifest dependency:** loader não necessariamente impede combinação incompleta; smoke é necessário.

## 13. Matriz de testes
- [ ] Cliente inicia com pfsable 1.0 + Presence Footsteps físico + Sable/Aeronautics atuais.
- [ ] Dedicated server não requer a bridge para gameplay.
- [ ] Caminhar sobre madeira, pedra e metal em ship produz som correspondente à superfície.
- [ ] Material modded representativo em contraption resolve sound type correto.
- [ ] Correr/sneak/jump-land não duplica ou perde footsteps.
- [ ] Cruzar mundo→ship e ship→mundo troca contexto imediatamente.
- [ ] Ship em movimento/rotação não faz lookup de bloco errado.
- [ ] Assembly/disassembly não deixa audio state stale.
- [ ] Reconnect/dimension change limpa contexto da contraption.
- [ ] Outros ambience/audio mods não produzem duplicação perceptível do mesmo footstep.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `pfsable-1.0.jar`, mod id/runtime e `pfsable.mixins.json`; Presence Footsteps, Sable e Create Aeronautics presentes.
- CurseForge oficial: project 1555269, file ID 8152593, Release NeoForge 1.21.1 de 27/05/2026, Environment Client, MIT.
- Página oficial: patch simples para usar Presence Footsteps sounds em Sable/Aeronautics contraptions e forks suportados.
- Relations oficiais: zero dependencies declaradas; Presence Footsteps/Sable são tratados aqui como **prerequisites funcionais**, não como hard dependencies manifestadas.
- **Limite:** internals de lookup/transform e compatibilidade com cada material/audio pack não foram inventados sem source/runtime específico.
