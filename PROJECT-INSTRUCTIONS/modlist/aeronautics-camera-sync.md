# Aeronautics Camera Sync

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812e91b4ce51a98dded2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Aeronautics Camera Sync
- **Arquivo JAR:** `aero_cam_sync-1.4.0.jar`
- **Versão 1.21.1:** `1.4.0`
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê operacional completo de camera frame, aim/throw/reach sync, Sable authority, client-only fallback, Player Tilt dependency e camera-overhaul risks confirmado no QC global #10.
- **Categoria:** Compat; QoL; Visual
- **Compatibilidade/Riscos:** Toca câmera, raycast/aim, throw direction e reach, portanto deve ser testado com First Person/camera overhaul mods. A linha 1.3.0 corrigiu incompatibilidades específicas com Camera Overhaul e Cut Through e crash de GameRendererPickMixin; não assumir que toda combinação de camera mod é universalmente segura. Create Aeronautics upstream declara visual issues com Iris shaders, mas isso é do stack Aeronautics, não uma incompatibilidade formal específica do Camera Sync.
- **Decisão:** Manter
- **Dependências:** Required upstream: Sable. Create Aeronautics é integração principal, mas foi tornado optional a partir da linha 1.3.x. No pack, Sable 2.0.5 e Create Aeronautics estão presentes. Aeronautics Player Tilt requer Camera Sync 1.4.0+ como base.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/aeronautics-camera-sync
- **Função:** Sincroniza a orientação da câmera e, em instalação cliente+servidor, direção de olhar/aim/throw/reach com a rotação de sublevels/contraptions Sable; banking, pitch e roll acompanham o deck móvel em vez de permanecerem presos ao frame global.
- **Histórico da decisão:** vazio
- **Observações:** Dossiê aprofundado concluído em 07/09/2026. Guia antigo 1.3.6 corrigido para JAR físico 1.4.0. Sable confirmado required; Create Aeronautics optional na metadata atual. Sem transformar visual issue de Iris no mod-base em incompatibilidade própria do Camera Sync.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Aeronautics Camera Sync 1.4.0 + changelogs da linha 1.3.x/1.4.0 + guia de tecnologia do projeto.
- **Sobreposição:** Complementa Player Tilt: Camera Sync cuida do frame de câmera/aim; Player Tilt aplica o frame ao corpo/hitbox/gravidade. Outros camera-overhaul mods podem atuar na mesma matriz de câmera. Não altera forças ou física da contraption.
- **Data da última decisão:** 2026-09-06

## Dossiê técnico de compatibilidade

### 1. Identidade auditada
- **Tipo:** compat/camera QoL para Sable/Aeronautics.
- **JAR:** `aero_cam_sync-1.4.0.jar`.
- **Runtime:** `1.4.0`.
- **Ambiente upstream:** cliente e servidor.

### 2. Descrição técnica detalhada
Aeronautics Camera Sync transforma a câmera do jogador para o frame local da estrutura Sable em que ele está. Quando a contraption banca, inclina ou sobe, a câmera acompanha a orientação em vez de permanecer alinhada ao mundo global.
A integração não é puramente cosmética quando instalada nos dois lados: o upstream documenta que a mesma rotação é aplicada à direção de olhar, lançamento de itens e cálculo de reach/aim. Assim, o ponto visual e o ponto autoritativo do servidor ficam coerentes.
O comportamento por instalação é importante:
- **cliente + servidor/singleplayer:** sync completo de câmera e direção de interação;
- **client-only:** tilt visual; ao segurar throwable/bucket, a inclinação é temporariamente desativada para evitar discrepância de trajetória;
- **server-only:** jogadores sem o mod não recebem efeito.

### 3. Superfícies tocadas
- camera transform;
- look vector;
- raycast/pick;
- throw direction;
- reach/interação;
- frame local Sable;
- configurações/blacklists de itens;
- compatibilidade com outros camera transformers.

### 4. Dependências
**Required upstream**
- Sable.

**Optional/integration target**
- Create Aeronautics. A partir da linha 1.3.x foi convertido de required para optional; o mod trabalha em estruturas Sable e Aeronautics é o consumidor principal no pack.

**Dependência reversa no pack**
- Aeronautics Player Tilt exige Camera Sync `1.4.0+`.

### 5. Compatibilidades e incompatibilidades conhecidas
O changelog da linha 1.3.0 registra fixes específicos para **Camera Overhaul**, **Cut Through** e um crash de `GameRendererPickMixin`. Isso é evidência de que a camada de câmera/raycast é sensível a composição, mas também de que esses casos conhecidos foram tratados.
Create Aeronautics, separadamente, documenta visual issues com Iris Shaders. Esse problema pertence ao stack Aeronautics/rendering e não é promovido nesta ficha a incompatibilidade formal do Camera Sync sem evidência específica.

### 6. Sobreposição e riscos no pack
**First Person / camera-overhaul stack:** qualquer mod que aplique transforms adicionais de câmera pode disputar ordem/matriz. Testar camera bob, roll, third/first-person transitions e aim.

**Player Tilt:** é complemento deliberado. Camera Sync fornece o frame/orientação; Player Tilt aplica inclinação física/visual ao corpo e hitbox. Remover Camera Sync quebra a dependência do Player Tilt.

**Throwables/buckets:** em client-only o próprio upstream mitiga discrepância desligando tilt enquanto esses itens são segurados. Isso evidencia que server-authoritative aim importa.

### 7. Matriz mínima de validação
1. banking/pitch/roll sobre airship;
2. aim de projétil com cliente+servidor;
3. throw de item/bucket;
4. reach em bloco/entidade durante rotação;
5. modo client-only;
6. First Person e demais camera mods ativos;
7. Camera Overhaul/Cut Through se presentes;
8. Player Tilt 0.1.3 simultâneo;
9. shader stack/Iris no contexto do Aeronautics;
10. sair/entrar da contraption e confirmar reset suave da câmera.

### 8. O que não faz
- não altera sustentação, massa, forças ou steering da contraption;
- não substitui Sable;
- não é o responsável por body/hitbox tilt do Player Tilt;
- não transforma o known visual issue de Iris/Aeronautics em conflito próprio automaticamente.

### 9. Evidência
- **Modlist física:** 1.4.0.
- **Upstream atual:** Sable required; full sync client/server; client-only fallback; direção de aim/throw/reach.
- **Changelog upstream:** fixes Camera Overhaul/Cut Through/GameRendererPickMixin e mudança Create Aeronautics→optional.
- **Guia de tecnologia:** descrição de câmera preservada, versão antiga substituída pela física.
